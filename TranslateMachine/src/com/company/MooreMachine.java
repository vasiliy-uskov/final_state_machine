package com.company;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

class MooreMachine {
    private MooreVertex[] vertices;
    private MooreEdge[] edges;

    MooreMachine(MooreVertex[] vertices, MooreEdge[] edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    void print(PrintWriter output) {
        output.println(Arrays
                .stream(this.vertices)
                .map((var vertex) -> vertex.value)
                .collect(Collectors.joining(" ")));
        var arguments = Arrays.stream(this.edges)
                .map(edge -> edge.argument)
                .distinct()
                .toArray(String[]::new);
        Arrays.sort(arguments);
        for (var argument : arguments) {
            var edges = Arrays.stream(this.edges)
                    .filter((var edge) -> edge.argument.equals(argument))
                    .toArray(MooreEdge[]::new);
            Arrays.sort(edges, Comparator.comparing(edge -> edge.startStateIndex));
            output.println(Arrays.stream(edges)
                    .map(edge -> this.vertices[edge.endStateIndex].name)
                    .collect(Collectors.joining(" ")));
        }
    }

    MealyMachine toMealyMachine() {
        var edges = Arrays.stream(this.edges).map(edge -> new MealyEdge(
                edge.startStateIndex,
                edge.endStateIndex,
                edge.argument,
                this.vertices[edge.endStateIndex].value
        )).toArray(MealyEdge[]::new);
        var vertices = Arrays.stream(this.vertices).map(vertex -> vertex.name).toArray(String[]::new);
        return new MealyMachine(vertices, edges);
    }
}
