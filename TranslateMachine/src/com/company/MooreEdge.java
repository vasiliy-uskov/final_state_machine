package com.company;

public class MooreEdge {
    public int startStateIndex;
    public int endStateIndex;
    public String argument;

    public MooreEdge(int startEdgeIndex, int endStateIndex, String argument) {
        this.startStateIndex =  startEdgeIndex;
        this.endStateIndex = endStateIndex;
        this.argument = argument;
    }
}
