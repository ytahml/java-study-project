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

    public static void main(String[] args) throws InterruptedException {
        test1();
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

}
