package com.zz.algorithm.sort;

/**
 * ************************************
 * create by Intellij IDEA
 * 常见排序算法：
 * 冒泡排序（Bubble Sort）
 * 插入排序（Insertion Sort）
 * 选择排序（Selection Sort）
 * 希尔排序（Shell Sort）
 * 快速排序（Quick Sort）
 * 归并排序（Merge Sort）
 * 堆排序（Heap Sort）
 * 计数排序（Counting Sort）
 * 桶排序（Bucket Sort）
 * 基数排序（Radix Sort）
 *
 * @author Francis.zz
 * @date 2021-07-29 11:44
 * ************************************
 */
public class SortAlgorithm {
    /**
     * 插入排序
     * 插人排序由 N - 1 趟排序组成。
     * 对于p = 1到 N-1 趟，插入排序保证从位置 0 到位置p上的元素为已排序状态。
     * 插入排序利用了这样的事实： 已知位置 0 到位置 p-1 上的元素已经处于排过序的状态
     *
     * 时间复杂度：O(N²)
     * 有序数组O(N)
     * 假设数组完全反序，那么N个数排序需要2+3+4+...+N次操作，等差数列之和为d/2*N²，去掉系数，O(N²)
     *
     * 1,2,5,6,3
     * 将3插入到5的位置，并把该位置及其之后的数据5和6依次往后挪动一次
     */
    public static void insertionSort(int[] unsortedData) {
        int opNum = 0;
        int j;
        for (int p = 1; p < unsortedData.length; p++) {
            int tmp = unsortedData[p];
            for (j = p; j > 0 && tmp < unsortedData[j - 1]; j--) {
                // 无需每次交换，只需把P位置上数据插入到合适位置，并把该位置及其之后的数据5和6依次往后挪动一次
                // SortUtils.swap(unsortedData, j, j - 1);
                unsortedData[j] = unsortedData[j - 1];
                opNum++;
            }
            unsortedData[j] = tmp;
        }
        System.out.println("opNum:" + opNum);
    }

    /**
     * 冒泡排序
     * 每次将1个最大的数置换到尾部
     * 引入标志位，如果上一轮冒泡没有发生置换，那么表明数组已经有序，无需再冒泡，有序数组只需一轮冒泡O(N)
     * 时间复杂度：O(N²)
     */
    public static void bubbleSort(int[] unsortedData) {
        int opNum = 0;
        for (int i = unsortedData.length - 1; i > 0; i--) {
            boolean flag = true;
            for (int j = 0; j < i; j++) {
                if(unsortedData[j] > unsortedData[j + 1]) {
                    SortUtils.swap(unsortedData, j, j + 1);
                    opNum++;
                    flag = false;
                }
            }
            if(flag) {
                break;
            }
        }
        System.out.println("opNum:" + opNum);
    }

    /**
     * 选择排序
     * 每次选择出一个最小的数，与数组头部位置的数据交换，依次往后
     * 不稳定，就算是有序数组，还是需要每次比对
     * 时间复杂度：O(N²)，空间复杂度：O(1)
     */
    public static void selectionSort(int[] unsortedData) {
        int opNum = 0;
        for (int i = 0; i < unsortedData.length; i++) {
            int min = i;
            for (int j = i; j < unsortedData.length; j++) {
                // 找到最小数据的位置
                min = unsortedData[j] < unsortedData[min] ? j : min;
                opNum++;
            }
            // 最小数据与数组头部数据交换
            SortUtils.swap(unsortedData, i, min);
        }
        System.out.println("opNum:" + opNum);
    }

    /**
     * 归并排序
     * 分段排序，归并结果。将数组分成2段，给每段排序，然后把已排序的段归并成完整数组，递归操作
     * 平均时间复杂度：O(NlogN)，空间复杂度：O(N)
     *
     * 数组： 5 1 4 3
     * 5 1 和 4 3
     * 1 5 与 3 4归并
     * 1 3 4 5
     */
    public static void mergeSort(int[] unsortedData) {
        int len = unsortedData.length;
        int[] result = new int[len];
        mergeSortRecursive(unsortedData, result, 0, len - 1);
    }

    private static void mergeSortRecursive(int[] arr, int[] tmpArr, int start, int end) {
        if (start < end) {
            // 找到中间位置，将数组切割为两份
            int center = (start + end) / 2;
            int leftEnd = center;
            int rightStart = center + 1;
            mergeSortRecursive(arr, tmpArr, start, leftEnd);
            mergeSortRecursive(arr, tmpArr, rightStart, end);

            // 将切割的两段归并到临时数组
            int tempOffset = start;
            while(start <= leftEnd && rightStart <= end) {
                tmpArr[tempOffset++] = arr[start] < arr[rightStart] ? arr[start++] : arr[rightStart++];
            }
            while(start <= leftEnd) {
                tmpArr[tempOffset++] = arr[start++];
            }
            while(rightStart <= end) {
                tmpArr[tempOffset++] = arr[rightStart++];
            }
            // 将临时数组的值赋值到原数组
            for (int i = 0; i < tempOffset; i++, end--) {
                arr[end] = tmpArr[end];
            }
        }
    }

    /**
     * 快速排序
     * 平均时间复杂度：O(NlogN)，空间复杂度：O(N)
     *
     * 将数组按基准值分为两份，左边小于基准值，右边大于基准值，递归操作
     *
     * 使用三数中值分割法取基准值：
     * 将left center right三个位置排序，
     * 然后将center位置上的数据与right-1位置互换，因为right位置的数据是大于center的，
     * 最后把right-1位置的数作为基准值
     *
     * 对于很小的数组(N≤20)，快速排序不如插入排序。不仅如此，因为快速排序是递归的，所以这样的情形经常发生。
     * 通常的解决方法是对于小的数组不使用递归的快速排序，而代之以诸如插人排序这样的对小数组有效的排序算法。
     * 使用这种策略实际上可以节省大约 15%( 相对于不用截止的做法而自始至终使用快速排序时）的运行时间。
     * 一种好的截止范围（cutoff range）是N = 10，虽然在 5 到 20 之间任一截止范围都有可能产生类似的结果。
     * 这种做法也避免了一些有害的退化情形，如取三个元素的中值而实际上却只有一个或两个元素的情况。
     *
     * 首尾都一样的情况
     */
    public static void quickSort(int[] unsortedData) {
        int len = unsortedData.length;

        quickSort(unsortedData, 0, len - 1);
    }

    private static void quickSort(int[] unsortedData, int left, int right) {
        // cutoff = 10，快速排序不如插入排序
        final int CUTOFF = 10;
        if((right - left) < CUTOFF) {
            SortAlgorithm.insertionSort(unsortedData);
        } else {
            int pivot = median3(unsortedData, left, right);

            // left+1(因为left已经小于基准值)和right-2(基准值在right - 1位置)开始与基准值比较。这种写法当unsortedData[i] = unsortedData[j]时会死循环
            /*int i = left + 1, j = right - 2;
            for (;;) {
                while(unsortedData[i] < pivot) {
                    i++;
                }
                while(unsortedData[j] > pivot) {
                    j--;
                }
                if(i < j) {
                    SortUtils.swap(unsortedData, i, j);
                }else {
                    break;
                }
            }*/

            int i = left, j = right - 1;
            for (;;) {
                while(unsortedData[++i] < pivot) {
                }
                while(unsortedData[--j] > pivot) {
                }
                if(i < j) {
                    SortUtils.swap(unsortedData, i, j);
                }else {
                    break;
                }
            }

            // 将基准值与i位置交换，此时i位置之前的数据都是小于基准值的，i位置之后的数据都大于基准值
            SortUtils.swap(unsortedData, i, right - 1);
            // 递归
            quickSort(unsortedData, left, i - 1);
            quickSort(unsortedData, i + 1, right);
        }
    }

    /**
     * 三数中值分割法取基准值
     * 将left center right三个位置排序，
     * 然后将center位置上的数据与right-1位置互换，因为right位置的数据是大于center的，
     * 最后把right-1位置的数作为基准值
     */
    private static int median3(int[] arr, int left, int right) {
        int center = (right + left) / 2;
        // 三数排序
        if(arr[center] < arr[left]) {
            SortUtils.swap(arr, center, left);
        }
        if(arr[right] < arr[left]) {
            SortUtils.swap(arr, right, left);
        }
        if(arr[right] < arr[center]) {
            SortUtils.swap(arr, right, center);
        }
        // 将right - 1位置上的数据与center交换，便于比较基准值
        SortUtils.swap(arr, right - 1, center);
        return arr[right - 1];
    }

    /**
     * 堆排序
     * 利用二叉堆的大顶堆，数组构建大顶堆，然后移除最大元，即将数组头部最大元与尾部交换位置，因为堆数据也是数组，并且移除最大元后正好尾部会空出一个位置
     * 这样就无需额外的数组空间和数组复制操作
     * 下滤操作再次构造大顶堆，循环执行上面的步骤
     *
     * 平均时间复杂度：O(NlogN)，空间复杂度：O(1)
     */
    public static void heapSort(int[] unsortedData) {
        // 先构建heap，每个有子节点的节点执行一次下滤操作，
        // 下标从0开始时，节点i的父节点为floor((i-1)/2)，最后一个节点为数组length - 1，因此最后一个节点的父节点为(length- 1-1)/2即unsortedData.length/2 - 1
        for (int i = unsortedData.length / 2 - 1; i >= 0; i--) {
            percolateDown(unsortedData, i, unsortedData.length);
        }

        for (int i = unsortedData.length - 1; i > 0; i--) {
            // 最大元与堆的最后一个元素位置调换
            SortUtils.swap(unsortedData, 0, i);
            // 节点下滤
            percolateDown(unsortedData, 0, i);
        }
    }

    /**
     * 指定节点下滤
     * 左子节点2*hole + 1
     */
    private static void percolateDown(int[] unsortedData, int hole, int length) {
        int tmp = unsortedData[hole];
        int child = 2 * hole + 1;
        for(;child < length;hole = child, child = 2 * hole + 1) {
            // child == length - 1时表示没有右节点了
            if(child != length - 1 && unsortedData[child] < unsortedData[child + 1]) {
                child++;
            }
            // 最大元
            if(unsortedData[child] > tmp) {
                unsortedData[hole] = unsortedData[child];
            } else {
                break;
            }
        }
        unsortedData[hole] = tmp;
    }

    public static void main(String[] args) {
        //int[] unsortedData = SortUtils.generateRandomArrayUnique(20, 20);
        int[] unsortedData = new int[]{11,1,2,5,9,11,12,12,13,20,55,3,5,11};
        SortUtils.printArray(unsortedData);
        heapSort(unsortedData);
        SortUtils.printArray(unsortedData);
    }
}
