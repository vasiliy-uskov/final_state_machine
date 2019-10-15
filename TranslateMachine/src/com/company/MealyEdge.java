package com.company;

public class MealyEdge {
    public int startStateIndex;
    public int endStateIndex;
    public String argument;
    public String value;

    public MealyEdge(int startStateIndex, int endStateIndex, String argument, String value) {
        this.startStateIndex = startStateIndex;
        this.endStateIndex = endStateIndex;
        this.argument = argument;
        this.value = value;
    }
}
