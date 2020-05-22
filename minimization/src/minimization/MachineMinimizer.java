package minimization;

import printers.TablePrinter;

import java.util.Formatter;
import java.util.function.IntBinaryOperator;

public class MachineMinimizer {
    public static int[] minimize(IntBinaryOperator getOutputSignal, IntBinaryOperator getEndVertex, int inputSignalsCount, int verticesCount) {
        int[][] verticesOutputs = getVerticesOutputs(getOutputSignal, inputSignalsCount, verticesCount);
        int[] markedVertices = markVertices(verticesOutputs);
        int[] prevMarkedVertices;
        do {
            prevMarkedVertices = markedVertices;
            int[][] markupTransitions = getMarkedVerticesTransitions(markedVertices, getEndVertex, inputSignalsCount);
            markedVertices = splitMarkup(markedVertices, markupTransitions);
            printStep(inputSignalsCount, verticesCount, prevMarkedVertices, markupTransitions, markedVertices);
        } while (!compare(prevMarkedVertices, markedVertices));
        return markedVertices;
    }

    private static void printStep(int inputSignalsCount, int verticesCount, int[] prevMarkedVertices, int[][] markupTransitions, int[] markedVertices) {
        String[][] table = new String[inputSignalsCount + 5][verticesCount];
        for (int i = 0; i < verticesCount; ++i) {
            table[0][i] =  "a" + (prevMarkedVertices[i] + 1);
            table[1][i] = "v" + (i + 1);
            table[table.length - 2][i] = table[2][i] = "=";
            table[table.length - 1][i] = "b" + (markedVertices[i] + 1);
        }
        for (int start = 0; start < verticesCount; start += 1) {
            for (int input = 0; input < inputSignalsCount; input += 1) {
                table[input + 3][start] = "a" + (markupTransitions[start][input] + 1);
            }
        }
        TablePrinter.print(new Formatter(System.out), table);
        System.out.println();
    }

    private static int[][] getMarkedVerticesTransitions(int[] markedVertices, IntBinaryOperator getEndVertex, int inputSignalsCount) {
        int[][] markupTransitions = new int[markedVertices.length][inputSignalsCount];
        for (int start = 0; start < markedVertices.length; start += 1) {
            for (int input = 0; input < inputSignalsCount; input += 1) {
                int endVertex = getEndVertex.applyAsInt(start, input);
                markupTransitions[start][input] = markedVertices[endVertex];
            }
        }
        return markupTransitions;
    }

    private static int[][] getVerticesOutputs(IntBinaryOperator getOutputSignal, int inputSignalsCount, int verticesCount) {
        int[][] verticesOutputs = new int[verticesCount][inputSignalsCount];
        for (int start = 0; start < verticesCount; ++start) {
            for (int input = 0; input < inputSignalsCount; ++input) {
                verticesOutputs[start][input] = getOutputSignal.applyAsInt(start, input);
            }
        }
        return verticesOutputs;
    }

    private static int[] splitMarkup(int[] markup, int[][] transitions) {
        int[] newMarkup = new int[transitions.length];
        for (int i = 0; i < transitions.length; ++i) {
            newMarkup[i] = -1;
        }
        int lastEqualityClassIndex = 0;
        for (int i = 0; i < transitions.length; ++i) {
            if (newMarkup[i] < 0) {
                newMarkup[i] = lastEqualityClassIndex;
                lastEqualityClassIndex++;
                for (int j = i; j < transitions.length; j += 1) {
                    if (newMarkup[j] < 0 && markup[i] == markup[j] && compare(transitions[i], transitions[j])) {
                        newMarkup[j] = newMarkup[i];
                    }
                }
            }
        }
        return newMarkup;
    }

    private static int[] markVertices(int[][] verticesValues) {
        int[] markedVertices = new int[verticesValues.length];
        for (int i = 0; i < verticesValues.length; ++i) {
            markedVertices[i] = -1;
        }
        int lastEqualityClassIndex = 0;
        for (int i = 0; i < verticesValues.length; ++i) {
            if (markedVertices[i] < 0) {
                markedVertices[i] = lastEqualityClassIndex;
                lastEqualityClassIndex++;
                for (int j = i; j < verticesValues.length; j += 1) {
                    if (markedVertices[j] < 0 && compare(verticesValues[i], verticesValues[j])) {
                        markedVertices[j] = markedVertices[i];
                    }
                }
            }
        }
        return markedVertices;
    }

    private static boolean compare(int[] arr1, int[] arr2) {
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
