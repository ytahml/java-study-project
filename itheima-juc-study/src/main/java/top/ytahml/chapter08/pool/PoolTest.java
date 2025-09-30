package top.ytahml.chapter08.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 测试线程池
 *
 * @author 花木凋零成兰
 * @since 2025/9/30 下午10:49
 */
@Slf4j
public class PoolTest {

    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(2, 1000, TimeUnit.SECONDS, 10);
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            threadPool.execute(() -> {
                log.debug("{}", finalI);
            });
        }
    }

}
