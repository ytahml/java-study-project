package top.ytahml.chapter04;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 花木凋零成兰
 * @title SequentialControl
 * @date 2025-07-15 21:51
 * @package top.ytahml.chapter04
 * @description 同步模式之顺序控制
 */
@Slf4j
public class SequentialControl {

    static final Object LOCK = new Object();
    // t2是否运行过
    static boolean t2_RUNNER = false;

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            synchronized (LOCK) {
                while (!t2_RUNNER) {
                    try {
                        LOCK.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                log.debug("1");
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            synchronized (LOCK) {
                log.debug("2");
                t2_RUNNER = true;
                LOCK.notifyAll();
            }
        }, "t2");

        t1.start();
        t2.start();

    }

}
