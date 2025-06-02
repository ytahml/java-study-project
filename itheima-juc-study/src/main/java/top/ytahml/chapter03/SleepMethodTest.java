package top.ytahml.chapter03;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 花木凋零成兰
 * @title SleepMethodTest
 * @date 2025-06-02 20:26
 * @package top.ytahml.chapter03
 * @description
 */
@Slf4j
public class SleepMethodTest {

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("running ...");
        }, "t1");

        t1.start();
        log.debug("t1 state: {}", t1.getState());

        // 使主线程休眠 1s，避免无法读取到 t1 线程的休眠状态
        Thread.sleep(1000);
        log.debug("t1 state: {}", t1.getState());

    }

}
