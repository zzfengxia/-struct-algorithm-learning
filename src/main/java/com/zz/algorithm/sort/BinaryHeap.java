package com.zz.algorithm.sort;

import com.zz.algorithm.tree.TreeUtils;

import java.util.Arrays;

/**
 * ************************************
 * create by Intellij IDEA
 * 二叉堆
 * 根节点为最小元，任意节点都要小于其后裔 -- 小顶堆
 * 根节点为最大元，任意节点都要大于其后裔 -- 大顶堆
 *
 * @author Francis.zz
 * @date 2021-08-03 15:13
 * ************************************
 */
public class BinaryHeap {
    private int[] ele;
    private int currentSize;
    private static final int DEFAULT_CAPACITY = 10;

    public BinaryHeap() {
        ele = new int[DEFAULT_CAPACITY];
        currentSize = 0;
    }

    public BinaryHeap(int capacity) {
        ele = new int[capacity];
        currentSize = 0;
    }

    public BinaryHeap(int[] data) {
        currentSize = data.length;
        ele = Arrays.copyOf(data, data.length);
        buildHeap();
    }

    /**
     * 构造二叉堆
     * 从一个节点元素任意放置的二叉树开始，自底向上对每一个子树执行删除根节点时的Max-Heapify算法（这是对最大堆而言）使得当前子树成为一个二叉堆
     */
    private void buildHeap() {
        for(int i = currentSize / 2 - 1; i >= 0; i--) {
            // 根节点下标为0，所有要减1
            percolateDown(i);
        }
    }

    /**
     * 小顶堆，根节点为最小元
     * 根节点从下标0开始，那么元素i的左子节点为2i+1，右子节点为2i+2，父节点为floor((i-1)/2)
     * 插入操作，先将元素x置入数组空闲的尾部，判断该穴位的父节点是否大于该元素，依次往上查找直到将该元素插入到合适节点位置
     */
    public void insert(int x) {
        if(currentSize == ele.length) {
            enlargeArray(ele.length * 2);
        }
        int hole = currentSize++;
        for(;hole > 0 && ele[(hole - 1) / 2] > x;hole = (hole - 1) / 2) {
            // 如果空穴父节点元素大于需要插入的元素，那就把父元素移入空穴，然后再向上查找
            ele[hole] = ele[(hole - 1) / 2];
        }
        ele[hole] = x;
    }

    /**
     * 弹出最小元
     * 删除根节点，此时根节点为空穴，找到其左右儿子节点的最小值，填入空穴，依次向下，
     * 直到节点值大于堆的最后一个元素，最后将堆的最后一个元素置入空穴
     */
    public int deleteMin() {
        if(isEmpty()) {
            throw new IllegalStateException("Heap is null");
        }
        int min = findMin();
        // 先把堆的最后一个元素置入空穴，并把推的节点数减1
        ele[0] = ele[--currentSize];
        percolateDown(0);

        return min;
    }

    /**
     * 指定穴位下滤
     * 从指定穴位开始下滤，找到其左右儿子节点的最小值，如果小于该穴位的值，则把儿子节点放入到该穴位，依次下推，直到把指定穴位的值下滤到合适穴位
     */
    private void percolateDown(int hole) {
        int tmp = ele[hole];
        // 子节点
        int child = 2 * hole + 1;
        // 只要有1个子节点就需要判断
        for(;child < currentSize;hole = child, child = 2 * hole + 1) {
            // child=currentSize-1时，不会再有后续节点
            if(child != currentSize - 1 && ele[child + 1] < ele[child]) {
                // 左节点大于右节点时，将子节点指向右节点
                child++;
            }
            if(ele[child] < tmp) {
                // 将子节点元素放入父节点，子节点置为空穴 hole = child
                ele[hole] = ele[child];
            } else {
                break;
            }
        }

        ele[hole] = tmp;
    }

    /**
     * 找到最小元
     * 由于这里是从数组下标0开始存放节点，因此小顶堆的最小元就是数组的第一个元素
     */
    public int findMin() {
        if(isEmpty()) {
            throw new IllegalStateException("Heap is null");
        }
        return ele[0];
    }

    /**
     * 扩容
     */
    private void enlargeArray(int newSize) {
        ele = Arrays.copyOf(ele, newSize);
    }

    public boolean isEmpty() {
        return currentSize == 0;
    }

    public int size() {
        return currentSize;
    }

    public static void main(String[] args) {
        int arr[] = new int[]{14,19,16,21,19,24,31,32,26,68,14};
        BinaryHeap heap = new BinaryHeap(arr);
        //heap.print(heap);
        int size = heap.size();
        for (int i = 0; i < size; i++) {
            //System.out.print(heap.deleteMin() + " ");
            heap.deleteMin();
            heap.print(heap);
        }
    }
    private void print(BinaryHeap heap) {
        Integer[] array = new Integer[heap.size()];
        for (int i = 0; i < heap.size(); i++) {
            array[i] = heap.ele[i];
        }
        TreeUtils.showGradeOnTree(TreeUtils.createTreeWithArray(array));
    }
}
