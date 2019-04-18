package com.demo.structures.linkedlist;

import lombok.Getter;
import lombok.Setter;

/**
 * 单链表操作
 *
 * @author guopeng
 * @create 2019/4/9
 */
public class SingleLinkedList {

    /**
     * 单链表反转
     *
     * @param curr 待反转链表
     * @return 反转后的链表
     */
    public static SingleLinkedNode reverse(SingleLinkedNode curr) {
        SingleLinkedNode newNode = null;
        SingleLinkedNode nextNode;

        // 当前结点不为空，则进行结点搬移
        while (curr != null) {
            // 记录临时结点 nextNode，标识为当前结点的下一个结点
            nextNode = curr.getNext();
            // 将当前结点的 next 指向 newNode
            curr.setNext(newNode);
            // 将 newNode 指向当前结点
            newNode = curr;
            // 将当前结点指向临时结点 nextNode
            curr = nextNode;
        }

        return newNode;
    }

    /**
     * 链表是否含有环
     * 快慢指针算法：当进入环之后，快的指针最终一定会追上慢的指针
     *
     * @param list 待检测链表
     * @return 是否包含环
     */
    public static boolean checkCircle(SingleLinkedNode list) {
        // 待检测为空，则不存在环
        if (list == null) {
            return false;
        }

        // 慢指针，每次移动一步
        SingleLinkedNode slow = list;
        // 快指针，每次移动两步
        SingleLinkedNode fast = list.getNext();

        while (slow != null && fast != null) {
            if (slow == fast) {
                return true;
            }
            slow = slow.getNext();
            fast = fast.getNext().getNext();
        }
        return false;
    }

    /**
     * 合并两个有序链表
     *
     * @param foo 第一个有序链表
     * @param bar 第二个有序链表
     * @return 已合并的有序链表
     */
    public static SingleLinkedNode mergeSortedList(SingleLinkedNode foo, SingleLinkedNode bar) {
        // 如果其中一个链表没有元素，直接返回另一个链表内容
        if (foo == null) {
            return bar;
        }
        if (bar == null) {
            return foo;
        }

        // 两个链表的追踪指针
        SingleLinkedNode fooPointer = foo;
        SingleLinkedNode barPointer = bar;
        // 头指针，用于最后结果返回
        SingleLinkedNode head;

        // 先比较两个链表的头结点，将最小的当做合并链表的头结点
        if (fooPointer.getData() < barPointer.getData()) {
            head = fooPointer;
            fooPointer = fooPointer.getNext();
        } else {
            head = barPointer;
            barPointer = barPointer.getNext();
        }
        // 尾指针，用于记录未合并完时，最后一个结点
        SingleLinkedNode end = head;

        // 依次判断两个链表的头结点大小，插入到合并链表的队尾，直到其中一个链表为空
        while (fooPointer != null && barPointer != null) {
            if (fooPointer.getData() < barPointer.getData()) {
                end.setNext(fooPointer);
                fooPointer = fooPointer.getNext();
            } else {
                end.setNext(barPointer);
                barPointer = barPointer.getNext();
            }
            end = end.getNext();
        }

        // 判断剩余链表是否还有元素，如果有，直接追加到队尾
        if (fooPointer != null) {
            end.setNext(fooPointer);
        } else {
            end.setNext(barPointer);
        }

        return head;
    }

    /**
     * 删除倒数第 K 个结点
     *
     * @param list 待处理链表
     * @param k 目标位置
     * @return 删除目标后的链表
     */
    public static SingleLinkedNode removeLastKth(SingleLinkedNode list, int k) {
        // 快指针先跑 K 步
        SingleLinkedNode fast = list;
        int index = 1;
        while (fast != null && index < k) {
            fast = fast.getNext();
            index++;
        }

        // 如果快指针为空，即刚好跑完 K 步或不足 K 步，链表不存在倒数第 K 个元素，直接返回原链表
        if (fast == null) {
            return list;
        }

        // 慢指针指向头结点
        SingleLinkedNode slow = list;
        SingleLinkedNode pre = null;

        // 如果快指针未跑到队尾：
        // - 前置结点指针指向慢指针当前结点
        // - 慢指针与快指针一同向后跑一步
        while (fast.getNext() != null) {
            pre = slow;
            slow = slow.getNext();
            fast = fast.getNext();
        }

        // 前置指针为空，代表快指针跑到队尾时，慢指针正好在队首，直接将头指针删除
        if (pre == null) {
            list = list.getNext();
        }
        // 否则，通过前置指针将慢指针所在位置删除
        else {
            pre.setNext(slow.getNext());
        }

        return list;
    }

    @Setter @Getter
    static class MiddleNodeStructure {
        // 链表长度是否为奇数
        private int size;
        // 链表长度为奇数，则只有 oddMiddleNode
        // 链表长度为偶数，oddMiddleNode 记录小的中间结点，evenMiddleNode 记录大的中间结点
        private SingleLinkedNode oddMiddleNode;
        private SingleLinkedNode evenMiddleNode;

        MiddleNodeStructure(int size, SingleLinkedNode middleNode) {
            this.size = size;
            if (size % 2 == 0) {
                oddMiddleNode = middleNode;
                evenMiddleNode = middleNode.getNext();
            } else {
                oddMiddleNode = middleNode;
                evenMiddleNode = null;
            }
        }

        void print() {
            String result = String.format("MiddleNode isOddSize: %s, oddMiddleNode: %d, evenMiddleNode: %d",
                    size,
                    oddMiddleNode.getData(),
                    size % 2 == 0 ? evenMiddleNode.getData() : null);
            System.out.println(result);
        }
    }

    public static MiddleNodeStructure findMiddleNode(SingleLinkedNode list) {
        // 如果链表为空，则没有中间结点
        if (list == null) {
            return null;
        }

        // 慢指针每回合走一步，快指针每回合走两步
        SingleLinkedNode slow = list;
        SingleLinkedNode fast = list;

        // 记录链表长度
        int size = 1;
        if (fast.getNext() != null) {
            size++;
            if (fast.getNext().getNext() != null) {
                size++;
            }
        }

        // 如果快指针跑两步也未跑到队尾，两指针一同向前跑一回合
        while (fast.getNext() != null && fast.getNext().getNext() != null) {
            slow = slow.getNext();
            fast = fast.getNext().getNext();

            // 追加记录链表长度
            if (fast.getNext() != null) {
                size++;
                if (fast.getNext().getNext() != null) {
                    size++;
                }
            }
        }

        return new MiddleNodeStructure(size, slow);
    }

    public static void reserveTest() {
        SingleLinkedNode list = SingleLinkedNode.initSingleLinkedList(1, 2, 3, 4);
        list.print();
        list = reverse(list);
        list.print();
    }

    public static void circleTest() {
        SingleLinkedNode circleList = new SingleLinkedNode(0);
        SingleLinkedNode circle1 = new SingleLinkedNode(1);
        SingleLinkedNode circle2 = new SingleLinkedNode(2);
        SingleLinkedNode circle3 = new SingleLinkedNode(3);

        circleList.addTail(circle1);
        circle1.setNext(circle2);
        circle2.setNext(circle3);
        circle2.setNext(circle1);
        System.out.println("checkCircle: " + checkCircle(circleList));
    }

    public static void mergeSortedListTest() {
        // foo: [1->5->9]
        SingleLinkedNode foo = SingleLinkedNode.initSingleLinkedList(1, 5, 9);
        System.out.print("foo: ");
        foo.print();

        // bar: [2->3->6]
        SingleLinkedNode bar = SingleLinkedNode.initSingleLinkedList(2, 3, 6);
        System.out.print("bar: ");
        bar.print();

        SingleLinkedNode mergedList = mergeSortedList(foo, bar);
        System.out.print("merged: ");
        mergedList.print();

    }

    public static void removeLastKthTest() {
        SingleLinkedNode list = SingleLinkedNode.initSingleLinkedList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        list.print();

        int k = 6;
        removeLastKth(list, k);
        System.out.print(String.format("remove last %dth element", k));
        list.print();
    }

    public static void findMiddleNodeTest() {
        SingleLinkedNode list = SingleLinkedNode.initSingleLinkedList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        list.print();
        MiddleNodeStructure middleNode = findMiddleNode(list);
        middleNode.print();
    }

    public static void main(String[] args) {
        reserveTest();
        circleTest();
        mergeSortedListTest();
        removeLastKthTest();
        findMiddleNodeTest();
    }
}
