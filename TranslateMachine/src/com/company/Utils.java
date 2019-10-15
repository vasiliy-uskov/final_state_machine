package com.company;

import java.util.function.BiPredicate;

public class Utils {
    public static String STATE_LETTER = "q";
    public static String VALUE_LETTER = "y";
    public static String CONSTANT_LETTER = "y";

    public static <T, D> int findIndex(T[] items, D item, BiPredicate<T, D> equal) {
        for (var i = 0; i < items.length; ++i) {
            if (equal.test(items[i], item))
            {
                return i;
            }
        }
        return -1;
    }
}
