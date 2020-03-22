package com.company;

class MealyEdge {
    int startStateIndex;
    int endStateIndex;
    String argument;
    String value;

    MealyEdge(int startStateIndex, int endStateIndex, String argument, String value) {
        this.startStateIndex = startStateIndex;
        this.endStateIndex = endStateIndex;
        this.argument = argument;
        this.value = value;
    }
}
