package top.ytahml.chapter08.juc;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import top.ytahml.utils.ThreadUtils;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * InvokeAny: 任务列表中有一个任务执行结束，则结束所有任务执行，并返回最先执行完任务的执行结果
 *
 * @author 花木凋零成兰
 * @since 2025/10/1 下午3:50
 */
@Slf4j
public class MethodInvokeAnyTest {
    @SneakyThrows
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(1);
        // 只有1个线程时，结果只会是第一个任务的执行结果，因为会先执行任务1，执行结束则直接终止
        String result = pool.invokeAny(Arrays.asList(
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
        log.info("executor result: {}", result);
    }
}
