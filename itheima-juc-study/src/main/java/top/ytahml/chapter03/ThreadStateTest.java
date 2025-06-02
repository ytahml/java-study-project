package top.ytahml.chapter03;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 花木凋零成兰
 * @title ThreadStateTest
 * @date 2025-06-02 20:22
 * @package top.ytahml.chapter03
 * @description 测试线程状态
 */
@Slf4j
public class ThreadStateTest {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            log.debug("running ...");
        }, "t1");

        System.out.println(t1.getState());

        t1.start();

        System.out.println(t1.getState());

    }

}
