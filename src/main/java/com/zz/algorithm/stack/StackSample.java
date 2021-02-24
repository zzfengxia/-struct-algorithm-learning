package com.zz.algorithm.stack;

import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * ************************************
 * create by Intellij IDEA
 * 桟（stack）是限制插人和删除只能在一个位置上进行的表（只有一端开放，另一端封死），该位置是表的末端，叫作栈的顶top。
 * 对栈的基本操作有 push（进栈）和 pop（出栈），前者相当于插入，后者则是删除最后插入的元素。
 * 栈又被叫作LIFO后进先出表。
 *
 * @author Francis.zz
 * @date 2021-02-24 11:49
 * ************************************
 */
public class StackSample {
    
    /**
     * 平衡符号：检查一段文本中符号（括号，大括号等）是否成对出现
     * 思路：
     * 创建一个空栈，遍历字符串的字符，如果字符是开放符号，则推入栈中。如果字符是封闭符号，此时栈为空则报错；
     * 否则弹出栈中元素，如果该元素不是对应封闭符号的开放符号则报错；当文本遍历结束时，栈不为空则报错。
     */
    public static void checkCharPairs(String text) {
        Map<String, String> signMap = new HashMap<>();
        signMap.put(")", "(");
        signMap.put("}", "{");
        signMap.put("]", "[");
        // 使用Deque双端队列模拟stack栈
        Deque<String> stack = new LinkedList<String>();
        Collection<String> openSign = signMap.values();
        Set<String> closeSign = signMap.keySet();
        for (int i = 0; i < text.length(); i++) {
            String c = text.substring(i, i + 1);
            if(openSign.contains(c)) {
                stack.push(c);
            } else if(closeSign.contains(c)) {
                if(stack.size() == 0) {
                    throw new IllegalArgumentException("符号对不匹配：" + c);
                }
                if(!signMap.get(c).equals(stack.pop())) {
                    throw new IllegalArgumentException("符号对不匹配：" + c);
                }
            }
        }
        if(stack.size() != 0) {
            throw new IllegalArgumentException("符号对不匹配：" + stack.pop());
        }
    }
    
    public static void main(String[] args) {
        String text = "{({1})anih[jj]k}";
        checkCharPairs(text);
    }
}
