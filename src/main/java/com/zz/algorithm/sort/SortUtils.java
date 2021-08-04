package com.zz.algorithm.sort;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * ************************************
 * create by Intellij IDEA
 *
 * @author Francis.zz
 * @date 2021-07-29 11:29
 * ************************************
 */
public class SortUtils {
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * 异或满足交换律
     * a ^ a = 0
     * a ^ 0 = a
     *
     * a = 5, b = 2 三次异或后
     * 5 ^ 2
     * b = 5 ^ 2 ^ 2 = 5
     * a = 5 ^ 2 ^ 5 ^ 2 ^ 2 = 2
     */
    public static void swap2(int[] arr, int i, int j) {
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
    }

    public static int[] generateRandomArray(int maxSize, int maxValue) {
        return generateRandomArray(maxSize, maxValue, false);
    }

    public static int[] generateRandomArrayUnique(int maxSize, int maxValue) {
        int length = Math.min(maxSize, maxValue);
        int[] arr = new int[length];
        Set<Integer> tmp = new HashSet<>();
        while(tmp.size() < length) {
            int num = (int) (maxValue* Math.random() + 1);
            if(tmp.add(num)) {
                arr[tmp.size() - 1] = num;
            }
        }
        tmp = null;

        return arr;
    }

    public static int[] generateRandomArray(int maxSize, int maxValue, boolean negative) {
        int[] arr = new int[maxSize];
        for (int i = 0; i < arr.length; i++) {
            // [-? , +?]
            if(negative) {
                arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
            } else {
                arr[i] = (int) ((maxValue + 1) * Math.random());
            }
        }
        return arr;
    }

    public static void printArray(int[] arr) {
        for (int data : arr) {
            System.out.print(data + " ");
        }
        System.out.println();
    }
}
