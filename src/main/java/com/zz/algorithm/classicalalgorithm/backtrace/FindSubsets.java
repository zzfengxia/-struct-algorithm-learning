package com.zz.algorithm.classicalalgorithm.backtrace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ************************************
 * create by Intellij IDEA
 * 求子集
 * 集合[1,2]的子集[],[1],[2],[1,2]
 *
 * @author Francis.zz
 * @date 2021-08-11 14:47
 * ************************************
 */
public class FindSubsets {
    /**
     * 位运算方法求一个集合的所有子集
     *
     * 集合[1,2]的子集[],[1],[2],[1,2]
     * 集合的每一个子元素都有显示和不显示两种选择，即0和1，
     * 且集合的子集数为2^n，n为集合元素数量，如果去掉空集合，那么子集数量为 2^n-1
     *
     * 时间复杂度：O(n*2^n)
     */
    public static <T> List<List<T>> findByBitMasking(T[] src) {
        // 子集数量为2^n
        int n = src.length;
        List<List<T>> result = new ArrayList<>(1 << n);

        for (int i = 0; i < 1 << n; i++) {
            List<T> sub = new ArrayList<>(n);
            for (int j = 0; j < n; j++) {
                if((i & (1 << j)) > 0) {
                    sub.add(src[j]);
                }
            }
            result.add(sub);
        }

        return result;
    }

    /**
     * 利用归纳思想解子集
     *
     * [1,2]的子集[],[1],[2],[1,2]
     * [1,2,3]的子集[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]
     * 可以看出[1,2,3]的子集为[1,2]的子集加上所有子集中放入元素3的子集之和，
     * 得出集合A = subset([1,2]) ，那么：
     * subset([1,2,3])
     * = A + [A[i].add(3) for i = 1..len(A)]
     * 可以使用递归实现，输入元素为空时是空集
     *
     * 时间复杂度：O(n*2^n)
     */
    public static <T> List<List<T>> findByInductive(T[] src) {
        List<List<T>> result = new ArrayList<>(1 << src.length);
        findByInductive(src, src.length, result);

        return result;
    }

    public static <T> void findByInductive(T[] src, int n, List<List<T>> result) {
        if(n == 0) {
            result.add(new ArrayList<>());
            return;
        }

        findByInductive(src, n - 1, result);
        int size = result.size();
        for (int i = 0; i < size; i++) {
            // 原集合的子集分别加入新元素
            List<T> sub = new ArrayList<>(result.get(i));
            sub.add(src[n - 1]);
            result.add(sub);
        }
    }

    /**
     * 回溯算法求子集，输入的数组无重复元素
     */
    public static <T> List<List<T>> subSetsByBackTracking(T[] src) {
        List<List<T>> result = new ArrayList<>(1 << src.length);
        List<T> tmp = new ArrayList<>();
        backTrace(src, 0, tmp, result);

        return result;
    }

    private static <T> void backTrace(T[] src, int start, List<T> tmp, List<List<T>> result) {
        result.add(new ArrayList<>(tmp));

        for (int i = start; i < src.length; i++) {
            // 选择
            tmp.add(src[i]);
            // 递归
            backTrace(src, i + 1, tmp, result);
            // 移除加入的元素，回退
            tmp.remove(tmp.size() - 1);
        }
    }

    /**
     * 回溯算法求子集，输入的数组有重复元素，子集不能重复
     */
    public static <T> List<List<T>> subSetsRepeat(T[] src) {
        List<List<T>> result = new ArrayList<>(1 << src.length);
        List<T> tmp = new ArrayList<>();
        // 排序
        Arrays.sort(src);

        backTraceRepeat(src, 0, tmp, result);

        return result;
    }

    private static <T> void backTraceRepeat(T[] src, int start, List<T> tmp, List<List<T>> result) {
        result.add(new ArrayList<>(tmp));

        for (int i = start; i < src.length; i++) {
            // 裁剪重复元素
            if(i > start && src[i] == src[i - 1])
                continue;
            // 选择
            tmp.add(src[i]);
            // 递归
            backTraceRepeat(src, i + 1, tmp, result);
            // 移除加入的元素，回退
            tmp.remove(tmp.size() - 1);
        }
    }

    public static void main(String[] args) {
        String[] src = {"a", "b", "b"};
        List<List<String>> result = subSetsRepeat(src);

        for (List<String> sub : result) {
            System.out.println(sub);
        }
    }
}
