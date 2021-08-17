package com.zz.algorithm.classicalalgorithm.backtrace;

import java.util.List;

/**
 * ************************************
 * create by Intellij IDEA
 *
 * @author Francis.zz
 * @date 2021-08-16 15:02
 * ************************************
 */
public class EightQueens {

    private static int queenPlace[];  //全局变量，下标表示行，值表示queen存储在那一列
    private static int count = 0;  //计数器

    private static void printQueen(int n) {  //打印一个二维数组
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (queenPlace[i] == j) {
                    System.out.print("Q ");
                } else {
                    System.out.print("* ");
                }
            }
            System.out.println();
        }
        System.out.println("----count:%d-----" + ++count);
    }

    private static boolean isOk(int row, int col, int n) {  //判断row行col列放置是否合适
        int leftUp = col - 1;  //左上对角线
        int rightUp = col + 1; //右上对角线
        for (int i = row - 1; i >= 0; --i) {
            if (queenPlace[i] == col) return false;  //同列上的格子有皇后
            if (leftUp >= 0) {
                if (queenPlace[i] == leftUp) return false;   //左上对角线有皇后
            }
            if (rightUp < n) {
                if (queenPlace[i] == rightUp) return false;  //右上对角线有皇后
            }
            --leftUp; ++rightUp;
        }
        return true;
    }

    private static void eightQueen(int row, int n) {
        if (row == n) {  //8个皇后都放置好，打印，无法递归返回
            printQueen(n);
            return;
        }
        for (int col = 0; col < n; ++col) {  //每一行都有8种方法
            System.out.printf("(%s,%s)%n", row, col);
            if (isOk(row, col, n)) {    //满足要求
                System.out.printf("ok: (%s,%s)%n", row, col);
                queenPlace[row] = col; //第row行的皇后放在col列
                eightQueen(row + 1, n);     //考察下一行
            }
        }
    }

    public List<List<String>> solveNQueens(int n) {
        return null;
    }

    public static void main(String[] args) {
        queenPlace = new int[4];
        eightQueen(0, 4);
    }
}
