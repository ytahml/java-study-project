package top.ytahml.chapter03;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 花木凋零成兰
 * @title InterruptMethodTest2
 * @date 2025-06-08 08:48
 * @package top.ytahml.chapter03
 * @description
 */
@Slf4j
public class InterruptMethodTest2 {

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            log.debug("sleep start ...");
            try {
                // sleep、wait、join 被打断后会清楚打断标记
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                log.warn(e.getMessage());
            }
            log.debug("sleep end ...");
        }, "t1");
        t1.start();

        // 主线程睡眠，保证打断t1线程时，t1是睡眠状态
        Thread.sleep(1000);
        log.debug("interrupt");
        t1.interrupt();
        Thread.sleep(1000);
        log.debug("打断标记: {}", t1.isInterrupted());

    }

}
