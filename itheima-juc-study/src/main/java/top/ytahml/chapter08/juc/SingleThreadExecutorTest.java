package top.ytahml.chapter08.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 单例线程池:
 * 可以保证系列任务的串行执行，当一个任务出现异常后，会创建一个新的线程继续执行任务
 * 使用装饰器模式，对线程池实例包装，不暴露核心线程数的修改方法
 * @author 花木凋零成兰
 * @since 2025/10/1 下午3:23
 */
@Slf4j
public class SingleThreadExecutorTest {

    public static void main(String[] args) {
        ExecutorService singleExecutor = Executors.newSingleThreadExecutor();
        singleExecutor.execute(() -> {
            log.debug("1");
            int i = 1 / 0;
        });
        singleExecutor.execute(() -> {
            log.debug("2");
        });
        singleExecutor.execute(() -> {
            log.debug("3");
        });
    }

}
