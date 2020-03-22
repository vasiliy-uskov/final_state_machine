package moor;

import minimization.MachineMinimizer;
import printers.TablePrinter;

import java.util.*;

public class MoorMachine {
    private final int[] outputs;
    private final int[][] transitions;
    private final int inputSignalsCount;
    private final int verticesCount;

    private MoorMachine(int[] outputs, int[][] transitions, int inputSignalsCount, int verticesCount) {
        this.outputs = outputs;
        this.transitions = transitions;
        this.inputSignalsCount = inputSignalsCount;
        this.verticesCount = verticesCount;
    }

    public MoorMachine minimize() {
        int[] markup = MachineMinimizer.minimize(this::getOutputSignal, this::getEndVertex, inputSignalsCount, verticesCount);
        int newVerticesCount = Arrays.stream(markup).max().getAsInt() + 1;
        int[][] newTransitions = new int[newVerticesCount][inputSignalsCount];
        int[] newOutputs = new int[newVerticesCount];
        for (int startVertex = 0; startVertex < verticesCount; ++startVertex) {
            newOutputs[markup[startVertex]] = outputs[startVertex];
            for (int inputSignal = 0; inputSignal < inputSignalsCount; ++inputSignal) {
                newTransitions[markup[startVertex]][inputSignal] = markup[transitions[startVertex][inputSignal]];
            }
        }
        return new MoorMachine(newOutputs, newTransitions, inputSignalsCount, newVerticesCount);
    }

    public void write(Formatter writer) {
        String[][] table = new String[inputSignalsCount + 1][verticesCount + 1];
        table[0][0] = "";
        for (int startVertex = 0; startVertex < verticesCount; ++startVertex) {
            table[0][startVertex + 1] = "v" + startVertex + "/" + "y" + outputs[startVertex];
        }
        for (int inputSignal = 0; inputSignal < inputSignalsCount; ++inputSignal) {
            table[inputSignal + 1][0] = "x" + inputSignal;
            for (int startVertex = 0; startVertex < verticesCount; ++startVertex) {
                int end = transitions[startVertex][inputSignal];
                table[inputSignal + 1][startVertex + 1] = "v" + end;
            }
        }
        TablePrinter.print(writer, table);
    }

    public static MoorMachine read(Scanner scanner, int inputSignalsCount, int verticesCount) {
        int[][] transitions = new int[verticesCount][inputSignalsCount];
        int[] outputs = new int[verticesCount];
        for (int i = 0; i < verticesCount; ++i) {
            outputs[i] =  scanner.nextInt();
        }
        for (int inputSignal = 0; inputSignal < inputSignalsCount; ++inputSignal) {
            for (int startVertex = 0; startVertex < verticesCount; ++startVertex) {
                transitions[startVertex][inputSignal] = scanner.nextInt();
            }
        }
        return new MoorMachine(outputs, transitions, inputSignalsCount, verticesCount);
    }

    private int getOutputSignal(int start, int input) {
        return outputs[start];
    }

    private int getEndVertex(int start, int input) {
        return transitions[start][input];
    }
}
