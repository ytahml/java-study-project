package top.ytahml.chapter08.juc;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import top.ytahml.utils.ThreadUtils;

import java.util.Arrays;
import java.util.Random;
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
//        CountDownLatch latch = new CountDownLatch(3);
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
//        ExecutorService service = Executors.newFixedThreadPool(4);
//        service.submit(() -> {
//            log.debug("start ...");
//            ThreadUtils.sleep(2000);
//            latch.countDown();
//            log.debug("end ...");
//        });
//        service.submit(() -> {
//            log.debug("start ...");
//            ThreadUtils.sleep(1500);
//            latch.countDown();
//            log.debug("end ...");
//        });
//        service.submit(() -> {
//            log.debug("start ...");
//            ThreadUtils.sleep(500);
//            latch.countDown();
//            log.debug("end ...");
//        });
//
//        service.submit(() -> {
//            log.info("waiting ... ");
//            try {
//                latch.await();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            log.warn("waited ...");
//        });

        // 等待多线程准备完毕
        ExecutorService service = Executors.newFixedThreadPool(10);
        // 控制等待
        CountDownLatch latch = new CountDownLatch(10);
        // 存储加载结果
        String[] all = new String[10];
        // 随机休眠时间
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            service.submit(() -> {
                executor(random, all, finalI);
                latch.countDown();
            });
        }
        latch.await();
        System.out.print("\n" + "游戏开始");

        // 手动结束线程池
        service.shutdown();
    }

    private static void executor(Random random, String[] all, int index) {
        for (int i = 0; i <= 100; i++) {
            // 模拟用户加载过程，随机睡眠
            ThreadUtils.sleep(random.nextInt(100));
            all[index] = i + "%";
            // 去掉换行，通过回车符覆盖原先打印结果
            System.out.print("\r" + Arrays.toString(all));
        }
    }

}
