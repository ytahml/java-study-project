package top.ytahml.chapter08.pool;

import lombok.extern.slf4j.Slf4j;
import top.ytahml.utils.ThreadUtils;

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
        ThreadPool threadPool = new ThreadPool(
                2,
                1000,
                TimeUnit.MILLISECONDS,
                10,
                (queue, task) -> {
                    // 1）队列满了死等
//                queue.put(task);
                    // 2）带超时时间的等待
//                    queue.offer(task, 1500, TimeUnit.MICROSECONDS);
                    // 3）放弃任务执行
//                    log.error("task drop: {}", task);
                    // 4）抛出异常
                    log.error("task execute exception: {}", task);
                    throw new RuntimeException("task execute exception: " + task);
                    // 5）调用者自己执行任务
//                    task.run();
                }
        );
        for (int i = 0; i < 15; i++) {
            int finalI = i;
            threadPool.execute(() -> {
                ThreadUtils.sleep(1000);
                log.info("{}", finalI);
            });
        }
    }

}
