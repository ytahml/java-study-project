package top.ytahml.chapter08.juc;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import top.ytahml.utils.ThreadUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 线程池，提交任务API
 *
 * @author 花木凋零成兰
 * @since 2025/10/1 下午3:35
 */
@Slf4j
public class SubmitMethodTest {

    @SneakyThrows
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Future<String> future = pool.submit(() -> {
            log.info("running ...");
            ThreadUtils.sleep(1000);
            return "OK!";
        });
        log.warn("future result: {}", future.get());
    }

}
