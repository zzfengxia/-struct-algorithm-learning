package com.zz.algorithm.classicalalgorithm.backtrace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * ************************************
 * create by Intellij IDEA
 * 全排列问题
 *
 * @author Francis.zz
 * @date 2021-08-13 17:00
 * ************************************
 */
public class Permutations {
    /**
     * 给定一个不含重复数字的数组 nums，返回其所有可能的全排列
     * 全排列解集数量=N!
     *
     * 示例 1：
     * 输入：nums = [1,2,3]
     * 输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
     * 示例 2：
     * 输入：nums = [0,1]
     * 输出：[[0,1],[1,0]]
     */
    public static List<List<Integer>> permute(int[] src) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> tmp = new ArrayList<>();

        backTrace(src, result, tmp);

        return result;
    }

    public static void backTrace(int[] src, List<List<Integer>> result, List<Integer> tmp) {
        if(tmp.size() == src.length) {
            result.add(new ArrayList<>(tmp));
            return;
        }

        for (int i = 0; i < src.length; i++) {
            if(tmp.contains(src[i])) {
               continue;
            }
            tmp.add(src[i]);

            backTrace(src, result, tmp);
            tmp.remove(tmp.size() - 1);

        }
    }

    public static void main(String[] args) {
        int[] src = {1,2,3};
        List<List<Integer>> result = permute(src);

        for (List<Integer> sub : result) {
            System.out.println(sub);
        }
    }
}
