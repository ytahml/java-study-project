package top.ytahml.chapter03;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 花木凋零成兰
 * @title TestMultiThread
 * @date 2025-06-02 10:03
 * @package top.ytahml.chapter03
 * @description 演示多个线程交替执行
 */
@Slf4j
public class TestMultiThread {

    public static void main(String[] args) {

        new Thread(() -> {
            while (true) {
                log.debug("running ...");
            }
        }, "t1").start();

        new Thread(() -> {
            while (true) {
                log.debug("running ...");
            }
        }, "t2").start();

    }

}
