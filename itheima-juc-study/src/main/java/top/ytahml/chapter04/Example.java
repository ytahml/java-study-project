package top.ytahml.chapter04;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 花木凋零成兰
 * @title Example
 * @date 2025-06-08 15:39
 * @package top.ytahml.chapter04
 * @description 测试并发访问共享变量
 */
@Slf4j
public class Example {

    static int counter = 0;

    static final Object LOCK = new Object();

    @SneakyThrows
    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                synchronized (LOCK) {
                    counter ++;
                }
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                synchronized (LOCK) {
                    counter --;
                }
            }
        }, "t2");
        t1.start();
        t2.start();

        t1.join();
        t2.join();
        log.debug("counter = {}", counter);



    }

}
