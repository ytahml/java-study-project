package top.ytahml.chapter03;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 花木凋零成兰
 * @title InterruptMethodTest3
 * @date 2025-06-08 08:59
 * @package top.ytahml.chapter03
 * @description
 */
@Slf4j
public class InterruptMethodTest3 {

    @SneakyThrows
    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            while (true) {
                log.debug("t1 running ...");
                boolean interrupted = Thread.currentThread().isInterrupted();
                if (interrupted) {
                    log.debug("t1 was interrupted ...");
                    break;
                }
            }
        }, "t1");
        t1.start();
        Thread.sleep(1000);
        log.debug("interrupt start ...");
        t1.interrupt();

    }

}
