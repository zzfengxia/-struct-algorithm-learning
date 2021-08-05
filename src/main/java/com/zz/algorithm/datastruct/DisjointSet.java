package com.zz.algorithm.datastruct;

import org.apache.commons.lang3.RandomUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ************************************
 * create by Intellij IDEA
 * 并查集，union/find
 * 使用不交集森林实现。数组中的每个元素即为一个节点，存放其父节点的引用，根节点存放空引用或者自身
 *
 * @author Francis.zz
 * @date 2021-08-04 17:22
 * ************************************
 */
public class DisjointSet {
    private int[] parent;
    private int[] rank;

    public DisjointSet(int eleNum) {
        // 初始化时每个节点的父节点都指向自身
        parent = new int[eleNum];
        for (int i = 0; i < eleNum; i++) {
            parent[i] = i;
        }
        // 初始化树的深度
        rank = new int[eleNum];
        Arrays.fill(rank, 1);
    }

    /**
     * 合并两个集合，x和y为两个集合的代表元素，即树的根节点
     * 简单合并：将其中一个节点的根节点的父节点引用指向另一个根节点 parent[x] = y;
     * 按秩合并：记录元素集根节点树的高度，高度小的合并到高度大的
     */
    public void union(int x, int y) {
        int rootX = parent[x];
        int rootY = parent[y];

        if(rootX == rootY) {
            return;
        }
        // 按秩合并
        if(rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else {
            parent[rootX] = rootY;
        }
        if(rank[rootX] == rank[rootY]) {
            rank[rootY]++;
        }
    }

    /**
     * 查找集合x的代表元素，即节点x的根节点。依次查找元素的父节点，直到父节点的引用指向自身
     */
    public int find(int x) {
        if(parent[x] == x) {
            return x;
        } else {
            // 路径压缩
            parent[x] = find(parent[x]);
            return parent[x];
        }
    }

    /**
     * 迷宫生成算法
     * 首先构造宽高都为基数的N×M地图，空白房间位置(X, Y),表示为(2 * x+1, 2 * y+1)（x范围[0, (height-1)/2])），房间四面都是墙
     * 先初始化所有房间到并查集，父节点指向自身
     * 把除四面最外墙以外的所有墙节点放入集合，随机取出一面墙，
     * 判断其左右或者上下房间是否联通，即查找两个房间的并查集父节点是否相同，
     * 如果不同就移除该墙，并把两个房间节点并查集合并，然后从存放墙的集合删除该墙。
     * 如果相同即已经联通，那么就直接从集合中删除该墙
     *
     *   0 1 2 3 4 5 6 7 8
     * 0 # # # # # # # # #
     * 1 #   #   #   #   #
     * 2 # # # # # # # # #
     * 3 #   #   #   #   #
     * 4 # # # # # # # # #
     * 5 #   #   #   #   #
     * 6 # # # # # # # # #
     */
    public static void main(String[] args) {
        int width = 15, height = 11;
        int[][] map = new int[height][width];
        // 2个字符表示一个单元，" #"为墙，"  "为空白房间，两个房间之间用墙分隔" #"，需要打通墙时将"  "表示
        // 所以可以通过给位置节点赋值0或1来表示是阻断还是联通状态，0位阻断，1位联通"  "
        // 宽高为 width,height，都为奇数
        // 空白房间位置(X, Y)，如(1, 1)都是基数单元（x当前高，y当前宽），可表示为(2 * x+1, 2 * y+1)，空白房间数(width-1)/2 * (height-1)/2

        // 房间数=(width-1)/2 * (height-1)/2，房间可以用当前节点宽高的索引表示 index = x * width + y
        List<Integer> roomSet = new ArrayList<>((width-1) / 2 * (height-1) / 2);
        List<Point> wallSet = new ArrayList<>(); // 内墙集合，二维表示，需要判断墙的方位，是上下还是左右有房间
        // 先初始化地图，初始化空白房间，四周是墙。地图节点位置索引可以表示为index = x * width + y
        for (int x = 0; x < height; x++) { // x为高
            for (int y = 0; y < width; y++) {
                if(x % 2 == 1 && y % 2 == 1) {
                    map[x][y] = 1;
                    roomSet.add(x * height + y);
                }else {
                    // 墙
                    map[x][y] = 0;

                    // 分隔两个房间的内墙，1奇1偶
                    if(x > 0 && x < height - 1 && y > 0 && y < width - 1 && (x + y) % 2 != 0) {
                        wallSet.add(new Point(x, y));
                    }
                }
            }
        }

        // 初始化并查集，初始房间数
        DisjointSet disjointSet = new DisjointSet(width * height);

        // 随机取出一面墙
        while(wallSet.size() > 0) {
            int random = RandomUtils.nextInt(0, wallSet.size());
            Point wall = wallSet.get(random);
            int[] roomIndex = getRoomNearWall(wall, width);

            // 并查集查找
            // 判断其左右或者上下房间是否联通，即查找两个房间的并查集父节点是否相同，
            // 如果不同就移除该墙，并把两个房间节点并查集合并，然后从存放墙的集合删除该墙。
            // 如果相同即已经联通，那么就直接从集合中删除该墙
            if(disjointSet.find(roomIndex[0]) != disjointSet.find(roomIndex[1])) {
                // 拆除墙
                map[(int) wall.getX()][(int) wall.getY()] = 1;
                // 合并两个节点
                disjointSet.union(roomIndex[0], roomIndex[1]);
            }

            wallSet.remove(wall);
        }

        for (int i = 0; i < map.length; i++) {
            for (int i1 = 0; i1 < map[i].length; i1++) {
                if(map[i][i1] == 0) {
                    System.out.print(" #");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }

    private static int[] getRoomNearWall(Point wall, int width) {
        int[] roomIndex = new int[2];
        int x = (int) wall.getX();
        int y = (int) wall.getY();
        if(x % 2 != 0) {
            roomIndex[0] = (x - 1) * width + y;
            roomIndex[1] = (x + 1) * width + y;
        } else if(y % 2 != 0) {
            roomIndex[0] = x * width + y + 1;
            roomIndex[1] = x * width + y - 1;
        }

        return roomIndex;
    }
}
