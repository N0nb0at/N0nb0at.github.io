package com.n0nb0at.saa.structure.array;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 手动实现数组，包含以下方法：
 *   1. 数组初始化
 *   2. 插入元素（队尾插入、指定位置插入）
 *   3. 删除指定位置元素
 *
 * @author guopeng
 * @create 2019/4/9
 */
public class Array {

    // 默认容量 8
    private static int DEFAULT_CAPACITY = 1 << 3;

    public int[] data;
    private int n;
    private int size;

    public Array() {
        this(DEFAULT_CAPACITY);
    }

    public Array(int capacity) {
        this.data = new int[capacity];
        this.n = capacity;
        this.size = 0;
    }

    /**
     * 重载插入，直接在尾部插入
     *
     * @param value 待插入元素值
     * @return 是否插入成功
     */
    public boolean insert(int value) {
        return insert(size + 1, value);
    }

    /**
     * 在指定位置插入元素
     *
     * @param index 目标位置
     * @param value 待插入元素值
     * @return 是否插入成功
     */
    public boolean insert(int index, int value) {
        // 空间不足
        if (n == size) {
            throw new IllegalArgumentException("数组空间不足");
        }

        // 插入位置不合法
        if (index < 0 || index > size + 1) {
            throw new IllegalArgumentException("插入位置不合法");
        }

        // 将目标位置及之后元素后移
        /*for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }*/
        // IDEA 推荐使用 System.arraycopy() 实现数组搬移
        if (size - index >= 0) {
            System.arraycopy(data, index, data, index + 1, size - index);
        }

        // 在目标位置插入值
        data[index] = value;
        size++;

        System.out.println(String.format("insert success, index: %d, value: %d", index, value));
        print();

        return true;
    }

    /**
     * 从指定位置删除元素
     *
     * @param index 目标位置
     * @return 是否删除成功
     */
    public boolean delete(int index) {
        // 待删除位置不合法
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("待删除位置不存在");
        }

        // 待删除元素值
        int deletedElement = data[index];

        // 将目标位置之后的元素前移
        /*for (int i = size; i >= index; i--) {
            data[i - 1] = data[i];
        }*/
        if (size + 1 - index >= 0) {
            System.arraycopy(data, index, data, index - 1, size + 1 - index);
        }

        size--;

        System.out.println(String.format("delete success, index: %d, deleted element: %d", index, deletedElement));
        print();

        return true;
    }

    /**
     * 输出当前内容
     */
    public void print() {
        StringBuilder sb = new StringBuilder();
        sb.append("Array size: ").append(size).append("\nArray content: ").append("[");
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(data[i]);
        }
        sb.append("]\n");
        System.out.println(sb.toString());
    }

}
