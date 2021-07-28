package com.zz.algorithm.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * ************************************
 * create by Intellij IDEA
 *
 *      1
 *    /  \
 *   2    3
 *  / \
 * 4   5
 * 遍历树：先序、中序、后序、层序，以根节点的先后顺序定义
 * 中序 Inorder (Left, Root, Right) : 4 2 5 1 3
 * 先序 Preorder (Root, Left, Right) : 1 2 4 5 3
 * 后序 Postorder (Left, Right, Root) : 4 5 2 3 1
 * 层序 Level Order Traversal : 1 2 3 4 5
 *
 * @author Francis.zz
 * @date 2021-07-28 15:06
 * ************************************
 */
public class TraverseTree {
    /**
     * 中序遍历：左，根，右
     */
    public static <T> void inOrderTraversal(TreeNode<T> root, List<T> result) {
        if(root == null) {
            return;
        }
        inOrderTraversal(root.left, result);
        result.add(root.element);
        inOrderTraversal(root.right, result);
    }

    /**
     * 先序遍历：根，左，右
     */
    public static <T> void preOrderTraversal(TreeNode<T> root, List<T> result) {
        if(root == null) {
            return;
        }
        result.add(root.element);
        preOrderTraversal(root.left, result);
        preOrderTraversal(root.right, result);
    }

    /**
     * 后序遍历：左，右，根
     */
    public static <T> void postOrderTraversal(TreeNode<T> root, List<T> result) {
        if(root == null) {
            return;
        }
        postOrderTraversal(root.left, result);
        postOrderTraversal(root.right, result);
        result.add(root.element);
    }

    /**
     * 层序遍历，每层节点顺序遍历
     *
     * @param isFill 空节点是否需要填充NULL
     */
    public static <T> void levelOrderTraversal(TreeNode<T> root, List<T> result, boolean isFill) {
        if(root == null) {
            return;
        }
        int depth = TreeUtils.getTreeDepth(root);
        // 保存上级节点
        Queue<TreeNode<T>> nodeQueue = new LinkedList<>();

        nodeQueue.offer(root);
        result.add(root.element);
        while(depth > 1) {
            int nodeSize = nodeQueue.size();
            for (int i = 0; i < nodeSize; i++) {
                TreeNode<T> cur = nodeQueue.poll();

                if(cur == null) {
                    return;
                }

                if(cur.left != null) {
                    nodeQueue.offer(cur.left);
                    result.add(cur.left.element);
                }
                if(cur.left == null && isFill) {
                    result.add(null);
                }

                if(cur.right != null) {
                    nodeQueue.offer(cur.right);
                    result.add(cur.right.element);
                }

                if(cur.right == null && isFill) {
                    result.add(null);
                }
            }
            depth--;
        }

        // 截掉最尾部的null
        boolean stop = false;
        for (int i = result.size() - 1; !stop && i > 0; i--) {
            if(result.get(i) == null) {
                result.remove(i);
            } else {
                stop = true;
            }
        }
    }

    public static void main(String[] args) {
        TreeNode<Integer> root = TreeUtils.createTreeWithArray(new Integer[]{5,4,8,11,null,13,4,7,2,null,1});
        TreeUtils.showGradeOnTree(root);

        List<Integer> result = new ArrayList<>();
        inOrderTraversal(root, result);
        System.out.println(result);

        result = new ArrayList<>();
        preOrderTraversal(root, result);
        System.out.println(result);

        result = new ArrayList<>();
        postOrderTraversal(root, result);
        System.out.println(result);

        result = new ArrayList<>();
        levelOrderTraversal(root, result, true);
        System.out.println(result);
    }
}
