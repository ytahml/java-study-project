package top.ytahml.chapter03;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 花木凋零成兰
 * @title StateTest
 * @date 2025-06-08 12:21
 * @package top.ytahml.chapter03
 * @description 测试线程的六种状态
 */
@Slf4j
public class StateTest {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            // new
        }, "t1");

        Thread t2 = new Thread(() -> {
            // runnable
            while (true);
        }, "t2");
        t2.start();

        Thread t3 = new Thread(() -> {
            // terminated
            log.debug("running");
        }, "t3");
        t3.start();

        Thread t4 = new Thread(() -> {
            synchronized (StateTest.class) {
                try {
                    // timed_waiting
                    Thread.sleep(10000000);
                } catch (InterruptedException e) {
                    log.debug(e.getMessage());
                }
            }
        }, "t4");
        t4.start();

        Thread t5 = new Thread(() -> {
            try {
                // waiting
                t2.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "t5");
        t5.start();

        Thread t6 = new Thread(() -> {
            synchronized (StateTest.class) {
                try {
                    // blocked
                    Thread.sleep(10000000);
                } catch (InterruptedException e) {
                    log.debug(e.getMessage());
                }
            }
        }, "t6");
        t6.start();

        log.debug("t1 线程状态: {}", t1.getState());
        log.debug("t2 线程状态: {}", t2.getState());
        log.debug("t3 线程状态: {}", t3.getState());
        log.debug("t4 线程状态: {}", t4.getState());
        log.debug("t5 线程状态: {}", t5.getState());
        log.debug("t6 线程状态: {}", t6.getState());

    }

}
