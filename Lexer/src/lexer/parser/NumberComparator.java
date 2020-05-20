package lexer.parser;

import java.util.Arrays;

public class NumberComparator {
    public static boolean equal(String a, String b) {
        return a.equals(b);
    }

    public static boolean greater(String a, String b) {
        return !lessOrEqual(a, b);
    }

    public static boolean less(String a, String b) {
        return !greater(a, b) && !equal(a, b);
    }

    public static boolean lessOrEqual(String a, String b) {
        a = eraseExtraSpaces(a);
        b = eraseExtraSpaces(b);
        var length = Math.max(a.length(), b.length());
        a = fillFreeSpaceWithZero(a, length).toUpperCase();
        b = fillFreeSpaceWithZero(b, length).toUpperCase();
        for (int i = 0; i < length; ++i) {
            if (isDigitLess(b.charAt(i), a.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isDigitLess(char a, char b) {
        String alphabet = "0123456789ABCDEF";
        var aPosition = alphabet.indexOf(a);
        var bPosition = alphabet.indexOf(b);
        return aPosition < bPosition;
    }

    private static String fillFreeSpaceWithZero(String str, int length) {
        char[] zeroArray = new char[length - str.length()];
        Arrays.fill(zeroArray, ' ');
        String zero = new String(zeroArray);
        return zero + str;
    }

    private static String eraseExtraSpaces(String str) {
        int lastZeroPosition = 0;
        while (lastZeroPosition < str.length() && str.charAt(lastZeroPosition) == '0') {
            lastZeroPosition++;
        }
        return str.substring(lastZeroPosition);
    }
}
