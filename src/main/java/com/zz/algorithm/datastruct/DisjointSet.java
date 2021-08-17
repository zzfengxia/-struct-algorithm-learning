package com.zz.algorithm.datastruct;

import org.apache.commons.lang3.RandomUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    //private int[] parent;
    //private int[] rank;
    private Map<Integer, Integer> parent; // 改为map
    private Map<Integer, Integer> rank;

    /*public DisjointSet(int eleNum) {
        // 初始化时每个节点的父节点都指向自身
        parent = new int[eleNum];
        for (int i = 0; i < eleNum; i++) {
            parent[i] = i;
        }
        // 初始化树的深度
        rank = new int[eleNum];
        Arrays.fill(rank, 1);
    }*/
    public DisjointSet(int eleNum) {
        // 初始化时每个节点的父节点都指向自身
        parent = new HashMap<>(eleNum);
        // 初始化树的深度
        rank = new HashMap<>(eleNum);
        for (int i = 0; i < eleNum; i++) {
            parent.put(i, i);
            rank.put(i, 1);
        }
    }

    public DisjointSet(List<Integer> ele) {
        // 初始化时每个节点的父节点都指向自身
        parent = new HashMap<>(ele.size());
        // 初始化树的深度
        rank = new HashMap<>(ele.size());
        for (Integer integer : ele) {
            parent.put(integer, integer);
            rank.put(integer, 1);
        }
    }

    /**
     * 合并两个集合，x和y为两个集合的代表元素，即树的根节点
     * 简单合并：将其中一个节点的根节点的父节点引用指向另一个根节点 parent[x] = y;
     * 按秩合并：记录元素集根节点树的高度，高度小的合并到高度大的
     */
    /*public void union(int x, int y) {
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
    }*/

    public void union(int x, int y) {
        int rootX = parent.get(x);
        int rootY = parent.get(y);

        if(rootX == rootY) {
            return;
        }
        // 按秩合并
        if(rank.get(rootX) > rank.get(rootY)) {
            parent.put(rootY, rootX);
        } else {
            parent.put(rootX, rootY);
        }
        if(rank.get(rootX).equals(rank.get(rootY))) {
            rank.put(rootY, rank.get(rootY) + 1);
        }
    }

    /**
     * 查找集合x的代表元素，即节点x的根节点。依次查找元素的父节点，直到父节点的引用指向自身
     */
    /*public int find(int x) {
        if(parent[x] == x) {
            return x;
        } else {
            // 路径压缩
            parent[x] = find(parent[x]);
            return parent[x];
        }
    }*/

    public int find(int x) {
        if(parent.get(x) == x) {
            return x;
        } else {
            // 路径压缩
            parent.put(x, find(parent.get(x)));
            return parent.get(x);
        }
    }

    /**
     * 迷宫生成算法
     *
     *   0 1 2 3 4 5 6 7 8
     * 0 # # # # # # # # #
     * 1 #   #   #   #   #
     * 2 # # # # # # # # #
     * 3 #   #   #   #   #
     * 4 # # # # # # # # #
     * 5 #   #   #   #   #
     * 6 # # # # # # # # #
     *
     * 设计思路：
     * 1. 先构建地图
     * 先创建一个N × M大小的地图，N和M均为奇数，设为高宽，height,width
     * 把地图使用墙分隔成小房间，房间数量为 (height-1)/2 * (width-1)/2，
     * 由上图可知空白房间位置(X, Y)，如(1, 1)，(3, 1)都是基数单元（x当前高，y当前宽）
     * 墙分外墙和内墙，外墙为四周四面墙，内墙用来分隔房间，由图可知内墙节点都是1奇1偶
     * 地图节点位置(x, y)可用索引表示 index = x * width + y
     *
     * 2. 利用并查集打通节点，制造迷宫
     * 将所有内墙节点保存到集合，随机取出一个内墙节点，利用并查集查找根节点来判断其分隔的左右或者上下两个房间是否连通，
     * 如果两个房间根节点不相同，则打通该墙，并把两个房间节点集合并到并查集，
     * 从集合中删除该墙，循环直到集合中没有墙节点。
     *
     * 3. 利用控制台打印迷宫
     * 使用二维数据表示迷宫地图，墙节点输出" #"，房间节点或者已经打通的墙节点使用"  "输出，
     * 即可用值1或0表示，1:联通，0:墙
     *
     */
    public static void main(String[] args) {
        int width = 31, height = 21;
        int[][] map = new int[height][width];
        // 2个字符表示一个单元，" #"为墙，"  "为空白房间，两个房间之间用墙分隔" #"，需要打通墙时将"  "表示
        // 所以可以通过给位置节点赋值0或1来表示是阻断还是联通状态，0位阻断，1位联通"  "
        // 宽高为 width,height，都为奇数
        // 空白房间位置(X, Y)，如(1, 1)都是基数单元（x当前高，y当前宽），空白房间数(width-1)/2 * (height-1)/2

        // 房间数=(width-1)/2 * (height-1)/2，房间可以用当前节点宽高的索引表示 index = x * width + y
        List<Integer> roomSet = new ArrayList<>((width-1) / 2 * (height-1) / 2);
        List<Point> wallSet = new ArrayList<>(); // 内墙集合，二维表示，需要判断墙的方位，是上下还是左右有房间
        // 先初始化地图，初始化空白房间，四周是墙。地图节点位置索引可以表示为index = x * width + y
        for (int x = 0; x < height; x++) { // x为高
            for (int y = 0; y < width; y++) {
                if(x % 2 == 1 && y % 2 == 1) {
                    map[x][y] = 1;
                    roomSet.add(x * width + y);
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
        DisjointSet disjointSet = new DisjointSet(roomSet);

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
        // 打通入口和出口，(1,0)和(height-2,width-1)
        map[1][0] = 1;
        map[height-2][width-1] = 1;

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
        //System.out.println("wall:" + wall);
        int[] roomIndex = new int[2];
        int x = (int) wall.getX(); // 高
        int y = (int) wall.getY(); // 宽
        if(x % 2 != 0) {
            roomIndex[1] = x * width + y - 1; // 左
            roomIndex[0] = x * width + y + 1; // 右
        } else if(y % 2 != 0) {
            roomIndex[0] = (x - 1) * width + y; // 上
            roomIndex[1] = (x + 1) * width + y; // 下
        }
        //System.out.println("roomIndex1:" + roomIndex[0]);
        //System.out.println("roomIndex2:" + roomIndex[1]);
        return roomIndex;
    }
}
