package top.ytahml.chapter03;

import lombok.extern.slf4j.Slf4j;

import static java.lang.Thread.sleep;

/**
 * @author 花木凋零成兰
 * @title JoinMethodTest
 * @date 2025-06-02 21:11
 * @package top.ytahml.chapter03
 * @description 测试Join方法
 * join：等待线程结束
 */
@Slf4j
public class JoinMethodTest {

    static int r = 0;

    static int r1 = 0;

    static int r2 = 0;

    public static void main(String[] args) throws InterruptedException {
//        test1();
//        test2();
        test3();
    }

    private static void test1() throws InterruptedException {
        log.debug("开始");
        Thread t1 = new Thread(() -> {
            log.debug("开始");
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("结束");
            r = 10;
        }, "t1");

        t1.start();
        t1.join();
        log.debug("结果为: {}", r);
        log.debug("结束");
    }

    // 同步等待多个线程执行结束
    private static void test2() throws InterruptedException {
        log.debug("开始");

        Thread t1 = new Thread(() -> {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            r1 = 10;
        }, "t1");
        Thread t2 = new Thread(() -> {
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            r2 = 20;
        }, "t2");
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        log.debug("r1结果为: {}, r2结果为: {}", r1, r2);
        log.debug("结束");
    }

    // 具有时效性的等待
    private static void test3() throws InterruptedException {
        log.debug("开始");

        Thread t1 = new Thread(() -> {
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            r = 10;
        }, "t1");

        t1.start();
        log.debug("join wait 1.5s");
        t1.join(1500);
        log.debug("join wait end ... ");
        log.debug("r = {}", r);
        log.debug("结束");
    }

}
