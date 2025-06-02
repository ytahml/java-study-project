package top.ytahml.chapter03;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author 花木凋零成兰
 * @title FutureTaskTest
 * @date 2025-06-02 09:56
 * @package top.ytahml.chapter03
 * @description 测试获取线程执行结果
 */
@Slf4j(topic = "c.FutureTaskTest")
public class FutureTaskTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<>(() -> {
            log.debug("running ...");
            Thread.sleep(2000);
            return 100;
        });
        Thread t = new Thread(task, "t1");
        t.start();

        Integer taskResult = task.get();
        log.debug("t1线程执行结果: {}", taskResult);

    }

}
