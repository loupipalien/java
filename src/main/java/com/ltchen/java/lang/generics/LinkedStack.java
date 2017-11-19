package com.ltchen.java.lang.generics;

/**
 * @author : ltchen
 * @date : 2017/11/20
 * @desc : 堆栈, 不再使用 LinkedList 实现内部链式存储
 */
public class LinkedStack<T> {

    private static class Node<T> {
        T item;
        Node<T> next;

        public Node() {
            item = null;
            next = null;
        }

        public Node(T item, Node<T> next) {
            this.item = item;
            this.next = next;
        }

        boolean end() {
            return item == null && next == null;
        }
    }

    /**
     * 末端哨兵
     */
    private Node<T> top = new Node<T>();

    public void push(T item) {
        top = new Node<T>(item, top);
    }

    public T pop() {
        T t = top.item;
        if (!top.end()) {
            top = top.next;
        }
        return t;
    }

    public static void main(String[] args) {
        String needBeSplitStr = "Phasers on stun!";
        String separator = " ";
        LinkedStack<String> ls = new LinkedStack<String>();
        for (String str : needBeSplitStr.split(separator)) {
            ls.push(str);
        }
        String str;
        while ((str = ls.pop()) != null) {
            System.out.println(str);
        }
    }
}
