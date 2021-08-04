package com.zz.algorithm.tree;

/**
 * ************************************
 * create by Intellij IDEA
 * 树节点
 *
 * @author Francis.zz
 * @date 2021-07-27 14:54
 * ************************************
 */
public class TreeNode<T> {
    T element;
    TreeNode<T> left;
    TreeNode<T> right;

    public TreeNode() {
    }

    public TreeNode(T element) {
        this.element = element;
    }

    public TreeNode(T element, TreeNode<T> left, TreeNode<T> right) {
        this.element = element;
        this.left = left;
        this.right = right;
    }
}
