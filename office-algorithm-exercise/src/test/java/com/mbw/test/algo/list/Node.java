package com.mbw.test.algo.list;

import lombok.Data;

/**
 * @author Mabowen
 * @date 2020-12-02 13:51
 */
@Data
public class Node<T> {
    //节点值
    private T value;

    //前一个节点
    private Node<T> prev;

    //后一个节点
    private Node<T> next;

    public Node(T value) {
        this.value = value;
    }

    public Node(T value, Node<T> prev, Node<T> next) {
        this.value = value;
        this.prev = prev;
        this.next = next;
    }
}
