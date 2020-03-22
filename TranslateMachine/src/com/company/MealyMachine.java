package com.company;

import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

class MealyMachine {
    private String[] vertices;
    private MealyEdge[] edges;

    MealyMachine(String[] vertices, MealyEdge[] edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    void print(PrintWriter output) {
        var arguments = Arrays.stream(this.edges)
                .map((var edge) -> edge.argument)
                .distinct()
                .toArray(String[]::new);
        Arrays.sort(arguments);
        for (var argument : arguments) {
            output.println(Arrays.stream(this.edges)
                    .filter((var edge) -> edge.argument.equals(argument))
                    .map((var edge) -> this.vertices[edge.endStateIndex] + edge.value)
                    .collect(Collectors.joining(" ")));
        }
    }

    MooreMachine toMooreMachine() {
        var verticesStream = Arrays
                .stream(this.edges)
                .map((var edge) -> {
                    String name = this.vertices[edge.endStateIndex];
                    String value = edge.value;
                    return new MooreVertex(name, value);
                }).distinct();
        var vertices = verticesStream.toArray(MooreVertex[]::new);
        Arrays.sort(vertices);

        var edges = new Vector<MooreEdge>();
        for (MealyEdge edge : this.edges) {
            var startVertex = this.vertices[edge.startStateIndex];
            var endVertex = this.vertices[edge.endStateIndex];
            int endEdgeIndex = Utils.findIndex(vertices, endVertex, (var v, var name) -> v.name.equals(name));
            for (int j = 0; j < vertices.length; ++j) {
                if (Objects.equals(vertices[j].name, startVertex)) {
                    edges.add(new MooreEdge(j, endEdgeIndex, edge.argument));
                }
            }
        }
        for (int i = 0; i < vertices.length; ++i) {
            vertices[i].name = Utils.STATE_LETTER + (i + 1);
        }
        return new MooreMachine(vertices, edges.toArray(MooreEdge[]::new));
    }
}
