package top.ytahml.chapter08.juc;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import top.ytahml.utils.ThreadUtils;

import java.util.concurrent.CountDownLatch;
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
        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 3; i++) {
            // 每次循环都得重新创建
            CountDownLatch latch = new CountDownLatch(2);
            service.submit(() -> {
                log.debug("task1 start ...");
                ThreadUtils.sleep(1000);
                latch.countDown();
            });
            service.submit(() -> {
                log.debug("task2 start ...");
                ThreadUtils.sleep(2000);
                latch.countDown();
            });
            latch.await();
            log.info("task1 and task2 finish ...");
        }
        service.shutdown();
    }

}
