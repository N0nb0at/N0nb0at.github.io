package com.n0nb0at.saa.structure.array;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author guopeng
 * @create 2019/4/16
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ArrayTests {

    @Test
    public void arrayTest() {
        Array array = new Array();
        array.insert(1);
        array.insert(2);
        array.insert(4);
        array.insert(8);

        array.delete(2);
    }
}
