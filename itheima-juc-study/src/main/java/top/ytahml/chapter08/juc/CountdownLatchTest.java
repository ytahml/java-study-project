package top.ytahml.chapter08.juc;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import top.ytahml.utils.ThreadUtils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CountdownLatch 用于进行线程同步协作，等待所有线程完成倒计时
 * await() 等待计数归零，countDown() 计数 - 1
 * @author 花木凋零成兰
 * @since 2025/10/9 22:01
 */
@Slf4j
public class CountdownLatchTest {

    @SneakyThrows
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(3);
/*
        new Thread(() -> {
            log.debug("t1 start ...");
            ThreadUtils.sleep(1000);
            latch.countDown();
            log.debug("t1 end ...");
        }, "t1").start();

        new Thread(() -> {
            log.debug("t2 start ...");
            ThreadUtils.sleep(2000);
            latch.countDown();
            log.debug("t2 end ...");
        }, "t2").start();

        new Thread(() -> {
            log.debug("t3 start ...");
            ThreadUtils.sleep(1500);
            latch.countDown();
            log.debug("t3 end ...");
        }, "t3").start();

        log.info("waiting ... ");
        latch.await();
        log.warn("waited ...");
        */

        // 使用线程池改进
        ExecutorService service = Executors.newFixedThreadPool(4);
        service.submit(() -> {
            log.debug("start ...");
            ThreadUtils.sleep(2000);
            latch.countDown();
            log.debug("end ...");
        });
        service.submit(() -> {
            log.debug("start ...");
            ThreadUtils.sleep(1500);
            latch.countDown();
            log.debug("end ...");
        });
        service.submit(() -> {
            log.debug("start ...");
            ThreadUtils.sleep(500);
            latch.countDown();
            log.debug("end ...");
        });

        service.submit(() -> {
            log.info("waiting ... ");
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.warn("waited ...");
        });


    }

}
