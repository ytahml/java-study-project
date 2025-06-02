package top.ytahml.chapter03;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 花木凋零成兰
 * @title YieldMethodAndPriorityTest
 * @date 2025-06-02 20:51
 * @package top.ytahml.chapter03
 * @description 测试线程优先级和 yield 方法
 */
@Slf4j
public class YieldMethodAndPriorityTest {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            int count = 0;
            for (; ; ) {
                log.debug("--------> 1 <--------- : {}", count++);
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            int count = 0;
            for (; ; ) {
//                Thread.yield();
                log.debug("--------> 2 <--------- : {}", count++);
            }
        }, "t2");

        t1.setPriority(Thread.MIN_PRIORITY);
        t2.setPriority(Thread.MAX_PRIORITY);

        t1.start();
        t2.start();

    }

}
