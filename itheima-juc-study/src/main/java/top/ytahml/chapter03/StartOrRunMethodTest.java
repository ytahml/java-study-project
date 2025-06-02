package top.ytahml.chapter03;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 花木凋零成兰
 * @title StartOrRunMethodTest
 * @date 2025-06-02 20:18
 * @package top.ytahml.chapter03
 * @description 测试线程的 start 和 run方法
 */
@Slf4j
public class StartOrRunMethodTest {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.debug("running ...");
        }, "t1");
        // run 方法是由调用方法的线程执行，不会启动新的线程来执行
        t1.run();

        // 会启动一个新线程来执行
        t1.start();

        log.debug("do other things ...");
    }

}
