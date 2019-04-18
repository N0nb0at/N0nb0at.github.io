package com.demo.structures.linkedlist;

import lombok.Data;

/**
 * 单链表结点数据结构
 *
 * @author guopeng
 * @create 2019/4/9
 */
@Data
public class SingleLinkedNode {
    private SingleLinkedNode next;
    private int data;

    /**
     * 默认空链表构造方法
     */
    public SingleLinkedNode() {
        this(null, -1);
    }

    /**
     * 指定参数的构造方法
     *
     * @param data 当前结点值
     */
    public SingleLinkedNode(int data) {
        this.next = null;
        this.data = data;
    }

    /**
     * 指定参数的构造方法
     *
     * @param next 下一节点
     * @param data 当前结点值
     */
    public SingleLinkedNode(SingleLinkedNode next, int data) {
        this.next = next;
        this.data = data;
    }

    /**
     * 将新节点加到头部
     *
     * @param node 新节点
     */
    public void addToHead(SingleLinkedNode node) {
        if (null == node) {
            throw new IllegalArgumentException("新增头结点不可为空");
        }

        node.setNext(this.getNext());
        this.setNext(node);
    }

    /**
     * 将新节点追加到尾部
     *
     * @param node 新结点
     */
    public void addTail(SingleLinkedNode node) {
        SingleLinkedNode end = this;
        while (end != null) {
            if (end.getNext() == null) {
                end.setNext(node);
                break;
            } else {
                end = end.getNext();
            }
        }
    }

    /**
     * 将新节点插入到指定节点之后
     *
     * @param node 新结点
     * @return 是否插入成功：找到数据相同的点，视为插入成功；反之失败
     */
    public boolean addAfter(SingleLinkedNode node) {
        SingleLinkedNode target = this;
        while (target != null) {
            if (target.getData() == node.getData()) {
                node.setNext(target.getNext());
                target.setNext(node);
                return true;
            } else {
                target = target.getNext();
            }
        }
        return false;
    }

    public static SingleLinkedNode initSingleLinkedList(int... value) {
        SingleLinkedNode list = new SingleLinkedNode(value[0]);
        for (int index = 1; index < value.length; index++) {
            list.addTail(new SingleLinkedNode(value[index]));
        }
        return list;
    }

    public void print() {
        StringBuilder sb = new StringBuilder();
        sb.append("LinkedList content: [").append(this.getData());
        SingleLinkedNode next = this.getNext();
        while (next != null) {
            sb.append("->").append(next.getData());
            next = next.getNext();
        }
        sb.append("]");
        System.out.println(sb);
    }
}
