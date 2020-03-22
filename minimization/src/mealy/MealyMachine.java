package mealy;

import minimization.MachineMinimizer;
import printers.TablePrinter;

import java.util.*;

public class MealyMachine {
    private final int[][] outputs;
    private final int[][] transitions;
    private final int inputSignalsCount;
    private final int verticesCount;

    private MealyMachine(int[][] outputs, int[][] transitions, int inputSignalsCount, int verticesCount) {
        this.outputs = outputs;
        this.transitions = transitions;
        this.inputSignalsCount = inputSignalsCount;
        this.verticesCount = verticesCount;
    }

    public MealyMachine minimize() {
        int[] markup = MachineMinimizer.minimize(this::getOutputSignal, this::getEndVertex, inputSignalsCount, verticesCount);
        int newVerticesCount = Arrays.stream(markup).max().getAsInt() + 1;
        int[][] newTransitions = new int[newVerticesCount][inputSignalsCount];
        int[][] newOutputs = new int[newVerticesCount][inputSignalsCount];
        for (int startVertex = 0; startVertex < verticesCount; ++startVertex) {
            for (int inputSignal = 0; inputSignal < inputSignalsCount; ++inputSignal) {
                newTransitions[markup[startVertex]][inputSignal] = markup[transitions[startVertex][inputSignal]];
                newOutputs[markup[startVertex]][inputSignal] = outputs[startVertex][inputSignal];
            }
        }
        return new MealyMachine(newOutputs, newTransitions, inputSignalsCount, newVerticesCount);
    }

    public void write(Formatter writer) {
        String[][] table = new String[inputSignalsCount + 1][verticesCount + 1];
        table[0][0] = "";
        for (int startVertex = 0; startVertex < verticesCount; ++startVertex) {
            table[0][startVertex + 1] = "v" + startVertex;
        }
        for (int inputSignal = 0; inputSignal < inputSignalsCount; ++inputSignal) {
            table[inputSignal + 1][0] = "x" + inputSignal;
            for (int startVertex = 0; startVertex < verticesCount; ++startVertex) {
                int end = transitions[startVertex][inputSignal];
                int output = outputs[startVertex][inputSignal];
                table[inputSignal + 1][startVertex + 1] = "v" + end + "/" + "y" + output;
            }
        }
        TablePrinter.print(writer, table);
    }

    public static MealyMachine read(Scanner scanner, int inputSignalsCount, int verticesCount) {
        int[][] transitions = new int[verticesCount][inputSignalsCount];
        int[][] outputs = new int[verticesCount][inputSignalsCount];
        for (int inputSignal = 0; inputSignal < inputSignalsCount; ++inputSignal) {
            for (int startVertex = 0; startVertex < verticesCount; ++startVertex) {
                transitions[startVertex][inputSignal] = scanner.nextInt();
                outputs[startVertex][inputSignal] =  scanner.nextInt();
            }
        }
        return new MealyMachine(outputs, transitions, inputSignalsCount, verticesCount);
    }

    private int getOutputSignal(int start, int input) {
        return outputs[start][input];
    }

    private int getEndVertex(int start, int input) {
        return transitions[start][input];
    }
}
