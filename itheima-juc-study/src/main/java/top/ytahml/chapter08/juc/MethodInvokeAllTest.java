package top.ytahml.chapter08.juc;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import top.ytahml.utils.ThreadUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * invokeAll 方法
 *
 * @author 花木凋零成兰
 * @since 2025/10/1 下午3:42
 */
@Slf4j
public class MethodInvokeAllTest {

    @SneakyThrows
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        List<Future<String>> futures = pool.invokeAll(Arrays.asList(
                () -> {
                    log.debug("begin 1 ... ");
                    ThreadUtils.sleep(1000);
                    return "1";
                },
                () -> {
                    log.debug("begin 2 ... ");
                    ThreadUtils.sleep(500);
                    return "2";
                },
                () -> {
                    log.debug("begin 3 ... ");
                    ThreadUtils.sleep(2000);
                    return "3";
                }
        ));
        futures.forEach(f -> {
            try {
                log.info("{}", f.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
