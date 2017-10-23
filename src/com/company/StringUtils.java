package com.company;

public class StringUtils {

    public static String repeat(String string, int repeat) {
        if (repeat <= 0) return "";
        return string + repeat(string, repeat - 1);
    }
}
