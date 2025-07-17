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

    static boolean run = true;

    public static void main(String[] args) {

        Thread t = new Thread(() -> {
            while (run) {
                // ...
//                log.debug("做一些处理...");
            }
        }, "t");

        t.start();

        sleep(3000);
        run = false;

    }

}
