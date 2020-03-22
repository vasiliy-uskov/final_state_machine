package printers;

import java.util.Formatter;

public class TablePrinter {
    public static void print(Formatter writer, String[][] table) {
        int maxLength = 0;
        for (int i = 0; i < table.length; ++i) {
            for (int j = 0; j < table[i].length; ++j) {
                maxLength = Math.max(maxLength, table[i][j].length());
            }
        }
        String format = "\n";
        for (int i = 0; i < table[0].length; ++i) {
            format = "%" + (maxLength + 2) + "s" + format;
        }
        for (String[] strings : table) {
            writer.format(format, strings);
        }
    }
}
