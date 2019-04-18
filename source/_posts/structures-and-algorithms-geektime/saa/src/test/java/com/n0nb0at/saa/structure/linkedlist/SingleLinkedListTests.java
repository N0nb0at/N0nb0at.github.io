package com.n0nb0at.saa.structure.linkedlist;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author guopeng
 * @create 2019/4/15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SingleLinkedListTests {

    @Test
    public void reserveTest() {
        SingleLinkedNode list = SingleLinkedNode.initSingleLinkedList(1, 2, 3, 4);
        list.print();
        list = SingleLinkedList.reverse(list);
        list.print();
    }

    @Test
    public void circleTest() {
        SingleLinkedNode circleList = new SingleLinkedNode(0);
        SingleLinkedNode circle1 = new SingleLinkedNode(1);
        SingleLinkedNode circle2 = new SingleLinkedNode(2);
        SingleLinkedNode circle3 = new SingleLinkedNode(3);

        circleList.addTail(circle1);
        circle1.setNext(circle2);
        circle2.setNext(circle3);
        circle2.setNext(circle1);
        System.out.println("checkCircle: " + SingleLinkedList.checkCircle(circleList));
    }

    @Test
    public void mergeSortedListTest() {
        // foo: [1->5->9]
        SingleLinkedNode foo = SingleLinkedNode.initSingleLinkedList(1, 5, 9);
        System.out.print("foo: ");
        foo.print();

        // bar: [2->3->6]
        SingleLinkedNode bar = SingleLinkedNode.initSingleLinkedList(2, 3, 6);
        System.out.print("bar: ");
        bar.print();

        SingleLinkedNode mergedList = SingleLinkedList.mergeSortedList(foo, bar);
        System.out.print("merged: ");
        mergedList.print();

    }

    @Test
    public void removeLastKthTest() {
        SingleLinkedNode list = SingleLinkedNode.initSingleLinkedList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        list.print();

        int k = 6;
        SingleLinkedList.removeLastKth(list, k);
        System.out.print(String.format("remove last %dth element", k));
        list.print();
    }

    @Test
    public void findMiddleNodeTest() {
        SingleLinkedNode list = SingleLinkedNode.initSingleLinkedList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        list.print();
        SingleLinkedList.MiddleNodeStructure middleNode = SingleLinkedList.findMiddleNode(list);
        middleNode.print();
    }
}
