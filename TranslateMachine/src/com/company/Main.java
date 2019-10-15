package com.company;

import java.io.*;
import java.util.Scanner;
import java.util.function.BiPredicate;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        var input = new Scanner(new FileInputStream(new File(args[0])));
        var output = new PrintWriter(new File(args[1]));
        var argumentsCount = input.nextInt();
        input.nextInt();
        var verticesCount = input.nextInt();
        var machineType = input.next();
        if (machineType.equals("mealy"))
        {
            readMealyMachine(input, argumentsCount, verticesCount).toMooreMachine().print(output);
        }
        if (machineType.equals("moore"))
        {
            readMoorMachine(input, argumentsCount, verticesCount).toMealyMachine().print(output);
        }

        input.close();
        output.close();
    }

    private static MealyMachine readMealyMachine(Scanner scanner, int argumentsCount, int verticesCount) {
        var vertices = new String[verticesCount];
        for (var i = 0; i < verticesCount; ++i) {
            vertices[i] = Utils.STATE_LETTER + i;
        }
        var edges = new MealyEdge[verticesCount * argumentsCount];
        for (var i = 0; i < edges.length; ++i) {
            var endStateValue = scanner.next().split(Utils.VALUE_LETTER);
            var endState = endStateValue[0];
            var value = Utils.VALUE_LETTER + endStateValue[1];
            var endEdgeIndex = Utils.findIndex(vertices, endState, String::equals);
            var argument = Utils.CONSTANT_LETTER + (i / verticesCount);
            edges[i] = new MealyEdge(i % verticesCount, endEdgeIndex, argument, value);
        }
        return new MealyMachine(vertices, edges);
    }

    private static MooreMachine readMoorMachine(Scanner scanner, int argumentsCount, int verticesCount) {
        var vertices = new MooreVertex[verticesCount];
        for (var i = 0; i < verticesCount; ++i) {
            vertices[i] = new MooreVertex(Utils.STATE_LETTER + i, scanner.next());
        }
        var edges = new MooreEdge[verticesCount * argumentsCount];
        for (var i = 0; i < edges.length; ++i) {
            var endEdgeIndex = Utils.findIndex(vertices, scanner.next(), (vertex, endEdge) -> vertex.name.equals(endEdge));
            var argument = Utils.CONSTANT_LETTER + (i / verticesCount);
            edges[i] = new MooreEdge(i % verticesCount, endEdgeIndex, argument);
        }
        return new MooreMachine(vertices, edges);
    }
}
