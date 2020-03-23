import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        var input = new Scanner(System.in);
        var output = new Formatter(System.out);
        var inputSignalsCount = input.nextInt();
        var verticesCount = input.nextInt();
        int[][][] machine = readMachine(input, inputSignalsCount, verticesCount);
        List<int[]> deterministicMachine = determinateMachine(machine, inputSignalsCount);
        printMachine(output, deterministicMachine, inputSignalsCount);
        input.close();
    }

    static void printMachine(Formatter out, List<int[]> machine, int inputSignalsCount) {
        String[][] table = new String[inputSignalsCount + 1][machine.size() + 1];
        for (int inputSignal = 0; inputSignal <= inputSignalsCount; ++inputSignal) {
            for (int startVertex = 0; startVertex <= machine.size(); ++startVertex) {
                if (inputSignal == 0 && startVertex == 0) {
                    table[inputSignal][startVertex] = "";
                }
                else if (inputSignal == 0) {
                    table[inputSignal][startVertex] = "v" + startVertex;
                }
                else if (startVertex == 0) {
                    table[inputSignal][startVertex] = "x" + inputSignal;
                }
                else {
                    var val = machine.get(startVertex - 1)[inputSignal - 1];
                    table[inputSignal][startVertex] = val < 0 ? "-" : "v" + val;
                }
            }
        }
        TablePrinter.print(out, table);
    }

    static List<int[]> determinateMachine(int[][][] nondeterministicMachine, int inputSignalsCount) {
        Queue<int[]> queue = new LinkedBlockingQueue<>();
        List<int[]> deterministicVertices = new ArrayList<>();
        List<int[][]> deterministicMachine = new ArrayList<>();
        queue.add(new int[]{0});
        while (!queue.isEmpty()) {
            int[] deterministicVertex = queue.poll();
            deterministicVertices.add(deterministicVertex);
            int[][] vertexTransitions = new int[inputSignalsCount][];
            deterministicMachine.add(vertexTransitions);
            for (int inputSignal = 0; inputSignal < inputSignalsCount; ++inputSignal) {
                int finalInputSignal = inputSignal;
                int[] newDeterministicVertex = Arrays.stream(deterministicVertex)
                        .flatMap(vertex -> Arrays.stream(nondeterministicMachine[vertex][finalInputSignal]))
                        .distinct()
                        .sorted()
                        .toArray();
                int[] processedVertex = find(deterministicVertices, vertex -> compare(vertex, newDeterministicVertex));
                int[] queueVertex = find(queue, vertex -> compare(vertex, newDeterministicVertex));
                int[] vertex = newDeterministicVertex;
                if (processedVertex != null) {
                    vertex = processedVertex;
                }
                else if (queueVertex != null) {
                    vertex = queueVertex;
                }
                if (vertex.length != 0 && queueVertex != vertex && processedVertex != vertex) {
                    queue.add(vertex);
                }
                vertexTransitions[inputSignal] = vertex;
            }
        }
        return deterministicMachine
                .stream()
                .map(transitions -> Arrays
                        .stream(transitions)
                        .mapToInt(deterministicVertices::indexOf)
                        .toArray())
                .collect(Collectors.toList());
    }

    static int[][][] readMachine(Scanner input, int inputSignalsCount, int verticesCount) {
        int[][][] machine = new int[verticesCount][inputSignalsCount][];
        for (int inputSignal = 0; inputSignal < inputSignalsCount; ++inputSignal) {
            for (int startVertex = 0; startVertex < verticesCount; ++startVertex) {
                List<Integer> transitions =  new ArrayList<>();
                var verticesStr = input.next();
                if (!verticesStr.equals("-")) {
                    var vertices = new Scanner(verticesStr);
                    vertices.useDelimiter("q");
                    while (vertices.hasNext()) {
                        transitions.add(vertices.nextInt());
                    }
                }
                machine[startVertex][inputSignal] = transitions.stream().mapToInt(v -> v).toArray();
            }
        }
        return machine;
    }

    static <T> T find(Collection<T> collection, Predicate<T> condition) {
        return collection.stream().filter(condition).findAny().orElse(null);
    }

    static boolean compare(int[] arr1, int[] arr2) {
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; ++i) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }
}
