package com.zz.algorithm.tree;

import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * ************************************
 * create by Intellij IDEA
 * 树相关操作工具类
 *
 * @author Francis.zz
 * @date 2021-07-27 14:58
 * ************************************
 */
public class TreeUtils {
    /**
     * 通过数组创建树结构，数组中元素按层级顺序存放，空节点需要null填充
     * [1,null,2,3]
     * [1,2]
     * [1,null,2]
     *
     * {5,4,8,11,null,13,4,7,2,null,null,null,1}
     *
     * 剪掉父节点为null的左右子节点的null填充情况下的数组
     * {5,4,8,11,null,13,4,7,2,null,1}
     *       5
     *      / \
     *     4   8
     *    /   / \
     *   11  13  4
     *  /  \      \
     * 7    2      1

     */
    public static <T> TreeNode<T> createTreeWithArray(T[] arr) {
        if(arr == null || arr.length == 0) {
            return null;
        }
        // 创建根节点
        TreeNode<T> root = new TreeNode<>(arr[0]);

        // 使用队列存放上一级的节点
        Queue<TreeNode<T>> parentNodeList = new LinkedList<>();
        // 数组中剩余元素数量
        int restArrSize = arr.length - 1;
        // 每层最大节点数量 = 上级非空节点数量 * 2
        int nodeSize = 1 * 2;
        // 每层起始节点在数组中的位置
        int gradeStart = 1;
        TreeNode<T> currentNode;
        parentNodeList.offer(root);

        while (restArrSize > 0) {
            for (int i = gradeStart; i < gradeStart + nodeSize; i++) {
                if (i == arr.length) {
                    // 遍历完成
                    return root;
                }
                currentNode = parentNodeList.poll();
                if(currentNode == null) {
                    return root;
                }

                if(arr[i] != null) {
                    currentNode.left = new TreeNode<>(arr[i]);
                    parentNodeList.offer(currentNode.left);
                }

                // 右节点
                i += 1;
                if (i == arr.length) {
                    // 遍历完成
                    return root;
                }
                if(arr[i] != null) {
                    currentNode.right = new TreeNode<>(arr[i]);
                    parentNodeList.offer(currentNode.right);
                }
            }
            gradeStart += nodeSize;
            restArrSize -= nodeSize;
            nodeSize = parentNodeList.size() * 2;
        }
        return root;
    }

    /**
     * 按层级结构将树节点输出为二维数组
     * 按下面的格式存放，空节点为空格
     *       5
     *      / \
     *     4   8
     *    /   / \
     *   11  13  4
     *  /  \      \
     * 7    2      1
     *
     *       1
     *     /   \
     *   2       3
     *  / \     / \
     * 4   5   6   7
     */
    public static <T> T[][] showGradeOnTree(TreeNode<T> root) {
        if(root == null) {
            return null;
        }
        int depth = getTreeDepth(root);


    }

    /**
     * 获取树的层数
     */
    public static <T> int getTreeDepth(TreeNode<T> root) {
        if(root == null) {
            return 0;
        }

        return 1 + Math.max(getTreeDepth(root.left), getTreeDepth(root.right));
    }
}
