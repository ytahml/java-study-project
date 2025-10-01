package top.ytahml.chapter08.juc;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import top.ytahml.utils.ThreadUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * shutdown、terminated等停止状态方法
 *
 * @author 花木凋零成兰
 * @since 2025/10/1 下午4:19
 */
@Slf4j
public class MethodShutdownTest {

    @SneakyThrows
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        pool.submit(() -> {
            log.debug("begin 1 ... ");
            ThreadUtils.sleep(1000);
            log.debug("end 1 ... ");
            return "1";
        });
        pool.submit(() -> {
            log.debug("begin 2 ... ");
            ThreadUtils.sleep(1000);
            log.debug("end 2 ... ");
            return "2";
        });
        pool.submit(() -> {
            log.debug("begin 3 ... ");
            ThreadUtils.sleep(1000);
            log.debug("end 3 ... ");
            return "3";
        });

        log.debug("shutdown");
        // 不会等待线程池的停止，接着往下执行
//        pool.shutdown();
        // 等待线程池停止，等够时间/线程池已停止，才接着往下执行
//        pool.awaitTermination(3, TimeUnit.SECONDS);
        // 正在运行的任务都会被打断；会返回任务队列中没有执行的任务
        List<Runnable> tasks = pool.shutdownNow();
        System.out.println(tasks);
        log.debug("other ...");
    }

}
