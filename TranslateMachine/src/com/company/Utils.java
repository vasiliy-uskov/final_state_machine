package com.company;

import java.util.function.BiPredicate;

class Utils {
    static String STATE_LETTER = "q";
    static String VALUE_LETTER = "y";
    static String CONSTANT_LETTER = "y";

    static <T, D> int findIndex(T[] items, D item, BiPredicate<T, D> equal) {
        for (var i = 0; i < items.length; ++i) {
            if (equal.test(items[i], item))
            {
                return i;
            }
        }
        return -1;
    }
}
