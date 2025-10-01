package top.ytahml.chapter08.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * JDK 提供的线程池工厂工具 Executors
 *
 * @author 花木凋零成兰
 * @since 2025/10/1 上午10:34
 */
@Slf4j
public class ExecutorsTest {

    public static void main(String[] args) {
        // 固定大小的线程池
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(2, new ThreadFactory() {
            private AtomicInteger t = new AtomicInteger(1);
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "imulan-pool-" + t.getAndIncrement());
            }
        });
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            newFixedThreadPool.execute(() -> {
                log.info("{}", finalI +1);
            });
        }
    }

}
