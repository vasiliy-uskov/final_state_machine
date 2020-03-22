package com.company;

class MooreEdge {
    int startStateIndex;
    int endStateIndex;
    String argument;

    MooreEdge(int startEdgeIndex, int endStateIndex, String argument) {
        this.startStateIndex =  startEdgeIndex;
        this.endStateIndex = endStateIndex;
        this.argument = argument;
    }
}
