package top.ytahml.chapter03;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 花木凋零成兰
 * @title DaemonThreadTest
 * @date 2025-06-08 10:54
 * @package top.ytahml.chapter03
 * @description 守护线程：任务线程停止后，守护线程直接结束
 */
@Slf4j
public class DaemonThreadTest {

    @SneakyThrows
    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
            }
            log.debug("线程结束 ...");
        }, "t1");
        t1.setDaemon(true);
        t1.start();

        Thread.sleep(3000);
        log.debug("结束 ...");


    }

}
