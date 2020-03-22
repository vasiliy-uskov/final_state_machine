import mealy.MealyMachine;
import moor.MoorMachine;

import java.util.Formatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var input = new Scanner(System.in);
        var output = new Formatter(System.out);
        var inputSignalsCount = input.nextInt();
        var verticesCount = input.nextInt();
        var machineType = input.next();
        if (machineType.equals("mealy"))
        {
             MealyMachine
                     .read(input, inputSignalsCount, verticesCount)
                     .minimize()
                     .write(output);
        }
        if (machineType.equals("moore"))
        {
             MoorMachine
                     .read(input, inputSignalsCount, verticesCount)
                     .minimize()
                     .write(output);
        }
        input.close();
    }

}
