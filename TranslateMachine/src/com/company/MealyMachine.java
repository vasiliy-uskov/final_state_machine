package com.company;

import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

public class MealyMachine {
    private String[] vertices;
    private MealyEdge[] edges;

    public MealyMachine(String[] vertices, MealyEdge[] edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    public void print(PrintWriter output) {
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

    public MooreMachine toMooreMachine() {
        var verticesStream = Arrays
                .stream(this.edges)
                .map((var edge) -> {
                    String name = this.vertices[edge.endStateIndex];
                    String value = edge.value;
                    return new MooreVertex(name, value);
                }).distinct();
        var vertices = verticesStream.toArray(MooreVertex[]::new);
        Arrays.sort(vertices);

        var edges = new MooreEdge[this.edges.length];
        for (int i = 0; i < this.edges.length; ++i) {
            var edge = this.edges[i];
            var startEdge = this.vertices[edge.startStateIndex];
            var endEdge = this.vertices[edge.endStateIndex];
            int startEdgeIndex = -1;
            int endEdgeIndex = -1;
            for (int j = 0; j < vertices.length; ++j) {
                if (Objects.equals(vertices[j].name, startEdge))
                {
                    startEdgeIndex = j;
                }
                if (Objects.equals(vertices[j].name, endEdge))
                {
                    endEdgeIndex = j;
                }
            }
            edges[i] = new MooreEdge(startEdgeIndex, endEdgeIndex, edge.argument);
        }
        for (int i = 0; i < vertices.length; ++i) {
            vertices[i].name = Constants.STATE_LETTER + (i + 1);
        }
        return new MooreMachine(vertices, edges);
    }
}
