package com.mbw.test.algo.list;

/**
 * @author Mabowen
 * @date 2020-12-02 09:52
 */
public class DoubleLink<T> {
    //链表长度
    private int size;

    //头结点
    private Node<T> head;

    public DoubleLink() {
        /**
         * 头结点不存储值 并且头结点初始化时 就一个头结点。
         * 所以头结点的前后节点都是自己
         * 并且这个链表的长度为0；
         */
        head = new Node<>(null, null, null);
        head.setPrev(head.getNext());
        head = head.getNext();
        size = 0;
    }

    public int getSize() {
        return this.size;
    }

    /**
     * 判断链表的长度是否为空
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 判断索引是否超出范围
     */
    public void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        return;
    }

    /**
     * 通过索引获取链表当中的节点
     */
    public Node<T> getNode(int index) {
        /**
         * 检查该索引是否超出范围
         */
        checkIndex(index);
        /**
         * 当索引的值小于该链表长度的一半时，那么从链表的头结点开始向后找是最快的
         */
        if (index < size / 2) {
            Node<T> cur = head.getNext();
            for (int i = 0; i < index; i++) {
                cur = cur.getNext();
            }
            return cur;
        }
        /**
         * 当索引值位于链表的后半段时，则从链表的另端开始找是最快的
         */
        /**
         * 此
         */
        Node<T> cur = head.getPrev();
        int newIndex = size - (index + 1);
        for (int i = 0; i < newIndex; i++) {
            cur = cur.getPrev();
        }
        return cur;
    }

    /**
     * 获取节点当中的值
     */
    public T getValue(Node<T> cur) {
        return cur.getValue();
    }

    /**
     * 获取第一个节点的值
     */
    public T getFirst() {
        return getValue(getNode(0));
    }

    /**
     * 获取最后一个节点的值
     */
    public T getLast() {
        return getValue(getNode(size - 1));
    }

    /**
     * 插入节点
     */
    public void insert(int index, T value) {
        //如果这次插入时 链表是空的
        if (index == 0) {
            //这个节点的
            Node<T> cur = new Node<T>(value, head, head.getNext());
            head.setNext(cur);
            head.getNext().setNext(cur);
            size++;
            return;
        }
        /**
         * 先根据给出的插入位置 找到该链表原来在此位置的节点
         */
        Node<T> node = getNode(index);
        /**
         *放置的位置的前一个节点就是原节点的前置节点 而后节点就是原节点
         */
        Node<T> cur = new Node<T>(value, node.getPrev(), node);
        /**
         * 现将该位置也就是 原节点的前节点的后节点 赋值成为新节点
         * 然后将新节点的后置节点的值赋值成为原节点
         */
        node.getPrev().setNext(cur);
        node.setPrev(cur);
        size++;
    }

    /**
     * 向表头插入数据
     */
    public void insertToHead(T Value) {
        insert(0, Value);
    }

    /**
     * 将元素插入到链表的尾部
     */
    public void insertToTail(T vlaue) {
        Node<T> cur = new Node<>(vlaue, head.getPrev(), head);
        //head.prev 代表原来的尾部节点
        //遵循两个原则 一 新插入节点的前一个节点的后一个节点为新节点。新节点的后一个节点的前一个节点是新节点
        head.setPrev(cur);
        head.getPrev().setNext(cur);
        size++;
    }

    /**
     * 删除节点的方法
     */
    public void del(int index) {
        checkIndex(index);
        Node<T> cur = getNode(index);
        //记住此时的指针还没断开 赋值以后才相当于断开
        cur.getPrev().setNext(cur.getNext());
        cur.getNext().setPrev(cur.getPrev());
        size--;
        cur = null;
        return;
    }

    /**
     * 删除第一个节点
     */
    public void delFirst() {
        del(0);
    }

    /**
     * 删除最后一个节点
     */
    public void delLast() {
        del(size - 1);
    }
}
