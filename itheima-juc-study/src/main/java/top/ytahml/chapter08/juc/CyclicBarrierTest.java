package top.ytahml.chapter08.juc;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import top.ytahml.utils.ThreadUtils;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 循环屏障：想将 task1 和 task2 反复执行三遍
 * @author 花木凋零成兰
 * @since 2025/10/12 20:50
 */
@Slf4j
public class CyclicBarrierTest {

    @SneakyThrows
    public static void main(String[] args) {
        // 线程数需要保持为 task 数一致，不然会导致线程任务的同时结束不符合预期，可能循环的第二个 task1 和第一个 task1 同时结束
        ExecutorService service = Executors.newFixedThreadPool(2);
        // CyclicBarrier 保证多个线程同时结束；当下一次继续调用 await() 时，又会重新计数

        CyclicBarrier barrier = new CyclicBarrier(2, () -> {
            log.info("task1 and task2 finish ...");
        });
        // 想要将相同任务反复执行时，不需要反复创建 CyclicBarrier
        for (int i = 0; i < 3; i++) {
            service.submit(() -> {
                log.debug("task1 start ...");
                ThreadUtils.sleep(1000);
                try {
                    // 次数-1
                    barrier.await();
                    log.debug("task1 end ...");
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            });
            service.submit(() -> {
                log.debug("task2 start ...");
                ThreadUtils.sleep(2000);
                try {
                    barrier.await();
                    log.debug("task2 end ...");
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            });
        }

//        for (int i = 0; i < 3; i++) {
//            // 每次循环都得重新创建
//            CountDownLatch latch = new CountDownLatch(2);
//            service.submit(() -> {
//                log.debug("task1 start ...");
//                ThreadUtils.sleep(1000);
//                latch.countDown();
//            });
//            service.submit(() -> {
//                log.debug("task2 start ...");
//                ThreadUtils.sleep(2000);
//                latch.countDown();
//            });
//            latch.await();
//            log.info("task1 and task2 finish ...");
//        }
        service.shutdown();
    }

}
