package com.zz.algorithm.util;

/**
 * ************************************
 * create by Intellij IDEA
 *
 * @author Francis.zz
 * @date 2021-08-05 11:22
 * ************************************
 */
public final class MyStringUtils {
    public static String repeatStr(String str, int repeat) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < repeat; i++) {
            sb.append(str);
        }

        return sb.toString();
    }
}
