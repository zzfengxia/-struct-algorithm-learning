package com.zz.algorithm.classicalalgorithm.backtrace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ************************************
 * create by Intellij IDEA
 * 组合问题
 * 集合[2,3, 5]中找到target为8的组合，元素可以重复
 * 组合结果：[2,2,2,2], [2,3,3],[3,5]
 *
 * @author Francis.zz
 * @date 2021-08-12 15:00
 * ************************************
 */
public class FindCombination {

    /**
     * 回溯算法解组合问题
     *
     * 无重复元素的数组和一个目标数，找出组合，数组的元素可以重复被选取，解集不能包含重复组合
     * [2,3, 5]中找到target为8
     */
    public static List<List<Integer>> findCombinationSum(int[] src, int target) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> tmp = new ArrayList<>();

        backTrace(src, target, 0, tmp, result);

        return result;
    }

    private static void backTrace(int[] src, int target, int start, List<Integer> tmp, List<List<Integer>> result) {
        if(target < 0) {
            return;
        }

        if(target == 0) {
            result.add(new ArrayList<>(tmp));
        }

        for (int i = start; i < src.length; i++) {
            // 选择
            tmp.add(src[i]);
            // 递归，组合元素可以重复，每次都从自身开始，target=target-当前元素
            backTrace(src, target-src[i], i, tmp, result);
            // 移除加入的元素，回退
            tmp.remove(tmp.size() - 1);
        }
    }

    /**
     * 回溯算法解组合问题
     *
     * 有重复元素的数组和一个目标数，找出组合，数组的元素只能使用一次，解集不能包含重复组合
     * [10,1,2,7,6,1,5]中找到target为8
     */
    public static List<List<Integer>> findCombinationSum2(int[] src, int target) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> tmp = new ArrayList<>();
        Arrays.sort(src);

        backTrace2(src, target, 0, tmp, result);

        return result;
    }

    private static void backTrace2(int[] src, int target, int start, List<Integer> tmp, List<List<Integer>> result) {
        if(target < 0) {
            return;
        }
        if(target == 0) {
            // 这里必须是new，不然存入的是tmp引用
            result.add(new ArrayList<>(tmp));
        }
        for (int i = start; i < src.length; i++) {
            // 解集组合不能重复，因此要裁掉重复元素
            if(i > start && src[i] == src[i - 1]) {
                continue;
            }
            tmp.add(src[i]);
            // 集合中的元素不能重复使用，因此这里要i+1
            backTrace2(src, target - src[i], i + 1, tmp, result);
            // 回退
            tmp.remove(tmp.size() - 1);
        }
    }

    public static void main(String[] args) {
        int[] src = {10,1,2,7,6,1,5};
        int[] src2 = {2,5,2,1,2};
        List<List<Integer>> result = findCombinationSum2(src2, 5);

        for (List<Integer> sub : result) {
            System.out.println(sub);
        }
    }
}
