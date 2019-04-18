package com.demo.structures.array;

import java.util.Arrays;

/**
 * @link https://github.com/jwasham/coding-interview-university/blob/master/translations/README-cn.md#%E6%95%B0%E7%BB%84arrays
 *
 * 实现一个动态数组（可自动调整大小的可变数组）：
 *     练习使用数组和指针去编码，并且指针是通过计算去跳转而不是使用索引
 *     通过分配内存来新建一个原生数据型数组
 *         - 可以使用 int 类型的数组，但不能使用其语法特性
 *         - 从大小为16或更大的数（使用2的倍数 —— 16、32、64、128）开始编写
 *     size() —— 数组元素的个数
 *     capacity() —— 可容纳元素的个数
 *     is_empty()
 *     at(index) —— 返回对应索引的元素，且若索引越界则愤然报错
 *     push(item)
 *     insert(index, item) —— 在指定索引中插入元素，并把后面的元素依次后移
 *     prepend(item) —— 可以使用上面的 insert 函数，传参 index 为 0
 *     pop() —— 删除在数组末端的元素，并返回其值
 *     delete(index) —— 删除指定索引的元素，并把后面的元素依次前移
 *     remove(item) —— 删除指定值的元素，并返回其索引（即使有多个元素）
 *     find(item) —— 寻找指定值的元素并返回其中第一个出现的元素其索引，若未找到则返回 -1
 *     resize(new_capacity) // 私有函数
 *         - 若数组的大小到达其容积，则变大一倍
 *         - 获取元素后，若数组大小为其容积的1/4，则缩小一半
 *     时间复杂度
 *         - 在数组末端增加/删除、定位、更新元素，只允许占 O(1) 的时间复杂度（平摊（amortized）去分配内存以获取更多空间）
 *         - 在数组任何地方插入/移除元素，只允许 O(n) 的时间复杂度
 *     空间复杂度
 *         - 因为在内存中分配的空间邻近，所以有助于提高性能
 *         - 空间需求 = （大于或等于 n 的数组容积）* 元素的大小。即便空间需求为 2n，其空间复杂度仍然是 O(n)
 *
 * @author guopeng
 * @create 2019/4/12
 */
public class DynamicArray<E> {

    /**
     * 数组元素个数
     *
     * @return 当前元素个数
     */
    public int size() {
        return size;
    }

    /**
     * 数组容量
     *
     * @return 当前容量
     */
    public int capacity() {
        return capacity;
    }

    /**
     * 数组是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 返回对应索引的元素
     *
     * @param index 目标索引
     * @return 对应元素
     */
    public E at(int index) {
        // 检查索引是否越界
        rangeCheck(index);
        return elementData(index);
    }

    /**
     * 向数组尾部增加元素
     *
     * @param item 待增加元素
     */
    public void push(E item) {
        // 容量检查
        resize(size + 1);
        // 增加元素
        elements[size++] = item;
    }

    /**
     * 向指定位置插入元素
     *
     * @param index 目标位置
     * @param item 待插入元素
     */
    public void insert(int index, E item) {
        // 检查目标索引是否越界
        rangeCheckForAdd(index);
        // 容量检查
        resize(size + 1);
        // 搬移目标位置及之后的元素
        System.arraycopy(elements, index, elements, index + 1, size - index);
        // 目标位置插入元素
        elements[index] = item;
        // 当前大小加 1
        ++size;

    }

    /**
     * 向数组首部增加元素
     *
     * @param item 待增加元素
     */
    public void prepend(E item) {
        // 检查容量是否需要扩增
        resize(size + 1);
        // 将数组向后搬移一位
        System.arraycopy(elements, 0, elements, 1, size);
        // 首元素设置为目标元素
        elements[0] = item;
        // 当前数组大小加 1
        ++size;
    }

    /**
     * 删除数组尾部元素并返回
     *
     * @return 尾部元素
     */
    public E pop() {
        // 没有元素后返回异常
        if (size == 0) {
            throw new IllegalArgumentException("数组已空!");
        }

        // 获取尾部元素
        int targetIndex = size - 1;
        E deletedElement = elementData(targetIndex);

        // 根据索引删除
        delete(targetIndex);
        return deletedElement;
    }

    /**
     * 根据索引删除元素
     *
     * @param index 待删除元素索引
     */
    public void delete(int index) {
        // 检查待删除索引是否越界
        rangeCheck(index);
        // 执行实际删除操作
        fastRemove(index);
        // 动态调整容量（其中 size 在 fastRemove() 执行过程中已经减 1，故目标大小即 size）
        resize(size);
    }
    
    /**
     * 删除指定的元素（与上述目标不同，实现方式参考 ArrayList）
     * 
     * @param item 待删除元素
     * @return 删除元素的索引，未找到对应元素返回 -1
     */
    public int remove(E item) {
        for (int index = 0; index < size; index++) {
            if (item == elements[index]) {
                fastRemove(index);
                return index;
            }
        }
        return -1;
    }

    /**
     * delete & remove 删除元素具体实现
     * 
     * @param index 待删除元素索引
     */
    private void fastRemove(int index) {
        // 需要移动的元素个数
        int numMoved = size - index - 1;
        // 需要移动的元素个数大于 0，则进行正式的搬移
        if (numMoved > 0) {
            System.arraycopy(elements, index + 1, elements, index, numMoved);
            // 搬移后，尾元素置空（clear to let GC do its work）
            elements[--size] = null;
        }
    }
    
    /**
     * 查找指定元素的索引
     * 
     * @param item 目标元素
     * @return 元素索引，未找到返回 -1
     */
    public int find(E item) {
        for (int index = 0; index < size; index++) {
            if (item == elements[index]) {
                return index;
            }
        }
        return -1;
    }

    /**
     * 根据目标大小调整容量
     *
     * @param targetSize
     */
    private void resize(int targetSize) {
        // 如果目标大小大于当前容量，将容量扩展至当前容量的 2 倍
        if (targetSize > capacity) {
            capacity = capacity << 1;
            elements = Arrays.copyOf(elements, capacity);
        }
        // 如果目标大小小于当前容量的 1/4 且大于默认容量，将容量缩减至当前容量的 1/2
        else if (targetSize < (capacity >> 2) && targetSize > DEFAULT_CAPACITY) {
            capacity = capacity >> 1;
            elements = Arrays.copyOf(elements, capacity);
        }
        // 其他情况不进行容量大小调整
    }

    /**
     * 检查是否越界
     *
     * @param index 索引
     */
    private void rangeCheck(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(outOfBoundMsg(index));
        }
    }

    /**
     * 增加元素时，目标位置是否越界
     *
     * @param index 索引
     */
    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(outOfBoundMsg(index));
        }
    }

    private String outOfBoundMsg(int index) {
        return String.format("index: %d, size: %d", index, size);
    }

    @SuppressWarnings("unchecked")
    private E elementData(int index) {
        return (E) elements[index];
    }

    /**
     * 数组默认容量
     */
    public static final int DEFAULT_CAPACITY = 1 << 4;

    /**
     * 数组内容元素
     */
    private Object[] elements;

    /**
     * 数组大小
     */
    private int size;

    /**
     * 当前数组容量
     */
    private int capacity;
}
