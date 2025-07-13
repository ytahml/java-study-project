package top.ytahml.chapter04.locks;

import lombok.extern.slf4j.Slf4j;

import static top.ytahml.utils.ThreadUtils.sleep;

/**
 * @author 花木凋零成兰
 * @title TestLiveLock
 * @date 2025-07-13 15:51
 * @package top.ytahml.chapter04.locks
 * @description
 */
@Slf4j
public class TestLiveLock {
    static volatile int count = 10;

    static final Object lock = new Object();
    public static void main(String[] args) {
        new Thread(() -> {
// 期望减到 0 退出循环
            while (count > 0) {
                sleep(1);
                count--;
                log.debug("count: {}", count);
            }
        }, "t1").start();
        new Thread(() -> {
// 期望超过 20 退出循环
            while (count < 20) {
                sleep(1);
                count++;
                log.debug("count: {}", count);
            }
        }, "t2").start();
    }

}
