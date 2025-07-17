package top.ytahml.chapter05;

import lombok.extern.slf4j.Slf4j;

import static top.ytahml.utils.ThreadUtils.sleep;

/**
 * @author 花木凋零成兰
 * @title NoExitTheLoop
 * @date 2025-07-17 21:49
 * @package top.ytahml.chapter05
 * @description 可见性问题，无法退出循环
 */
@Slf4j
public class NoExitTheLoop {

    // volatile 易变
    static boolean run = true;

    static final Object LOCK = new Object();

    public static void main(String[] args) {

        Thread t = new Thread(() -> {
            while (true) {
                // ...
//                log.debug("做一些处理...");
                synchronized (LOCK) {
                    if (!run) {
                        break;
                    }
                }
            }
        }, "t");

        t.start();

        sleep(3000);
        log.debug("停止t");
        synchronized (LOCK) {
            run = false;
        }

    }

}
