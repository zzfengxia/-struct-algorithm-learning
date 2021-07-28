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
     * [1,null,2,3] 最后一行剪掉父节点为null的左右子节点的null填充情况下的数组
     * [1,2]
     * [1,null,2]
     *
     * {5,4,8,11,null,13,4,7,2,null,null,null,1}
     *
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
     *
     * 最后一行每个节点间隔3个空格，树的深度为d，因此最后1行的宽度为最大节点数 * 3 + 1，即 2^(d-1) * 3 + 1，也是二维数组的宽度
     * 每行间隔字符数gap = 树深度 - 当前节点层数 - 1
     * 第一行起始位置 宽度/2，往下每行左节点位置 = 起始位置 - gap，右节点 = 起始位置 + gap
     */
    public static <T> String[][] showGradeOnTree(TreeNode<T> root) {
        if (root == null)
            return null;

        int treeDepth = getTreeDepth(root);

        int arrayHeight = treeDepth * 2 - 1;
        // 最后一行的宽度为2的（n - 1）次方乘3，再加1
        int arrayWidth = (2 << (treeDepth - 2)) * 3 + 1;
        String[][] res = new String[arrayHeight][arrayWidth];
        // 对数组进行初始化，默认为一个空格
        for (int i = 0; i < arrayHeight; i ++) {
            for (int j = 0; j < arrayWidth; j ++) {
                res[i][j] = " ";
            }
        }

        // 从根节点开始，递归处理整个树
        writeArray(root, 0, arrayWidth/ 2, res, treeDepth);

        for (String[] line: res) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < line.length; i ++) {
                sb.append(line[i]);
                if (line[i].length() > 1 && i <= line.length - 1) {
                    i += line[i].length() > 4 ? 2: line[i].length() - 1;
                }
            }
            System.out.println(sb);
        }
        return res;
    }

    private static <T> void writeArray(TreeNode<T> currNode, int rowIndex, int columnIndex, String[][] res, int treeDepth) {
        if (currNode == null)
            return;

        res[rowIndex][columnIndex] = String.valueOf(currNode.element);

        // 计算当前位于树的第几层
        int currLevel = ((rowIndex + 1) / 2);
        // 若到了最后一层，则返回
        if (currLevel == treeDepth)
            return;

        // 计算当前行到下一行，每一个元素之间的间隔（下一行的列索引与当前元素的列索引之间的间隔）
        int gap = treeDepth - currLevel - 1;

        // 对左儿子进行判断，如有左儿子，则记录相应的"/"与左儿子的值
        if (currNode.left != null) {
            res[rowIndex + 1][columnIndex - gap] = "/";
            writeArray(currNode.left, rowIndex + 2, columnIndex - gap * 2, res, treeDepth);
        }

        // 对右儿子进行判断，如有右儿子，则记录相应的"\"与右儿子的值
        if (currNode.right != null) {
            res[rowIndex + 1][columnIndex + gap] = "\\";
            writeArray(currNode.right, rowIndex + 2, columnIndex + gap * 2, res, treeDepth);
        }
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

    public static void main(String[] args) {
        //   * [1,null,2,3]
        //     * [1,2]
        //     * [1,null,2]
        TreeNode<Integer> root = createTreeWithArray(new Integer[]{1,2,3,4,5});
        TreeNode<Integer> root2 = createTreeWithArray(new Integer[]{5,4,8,11,null,13,4,7,2,null,null,null,1});
        TreeNode<Integer> root3 = createTreeWithArray(new Integer[]{5,4,8,11,null,13,4,7,2,null,1});
        showGradeOnTree(root);
        showGradeOnTree(root2);
        showGradeOnTree(root3);
    }
}
