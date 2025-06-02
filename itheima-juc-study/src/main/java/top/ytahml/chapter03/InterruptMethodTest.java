package top.ytahml.chapter03;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author 花木凋零成兰
 * @title InterruptMethodTest
 * @date 2025-06-02 20:34
 * @package top.ytahml.chapter03
 * @description interrupt 打断线程方法
 */
@Slf4j
public class InterruptMethodTest {

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            log.debug("enter sleep ...");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                log.debug("wake up ...");
                throw new RuntimeException(e);
            }
        }, "t1");

        t1.start();

        Thread.sleep(1000);
        log.debug("interrupt ...");
        t1.interrupt();

    }

}
