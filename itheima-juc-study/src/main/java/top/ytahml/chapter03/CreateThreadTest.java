package top.ytahml.chapter03;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 花木凋零成兰
 * @title CreateThreadTest
 * @date 2025-05-24 17:41
 * @package top.ytahml.chapter03
 * @description 创建线程测试
 */
@Slf4j(topic = "CreateThreadTest")
public class CreateThreadTest {

    public static void main(String[] args) {

        Thread t = new Thread() {
            @Override
            public void run() {
                log.debug("running");
            }
        };
        t.setName("t1");
        t.start();

        log.debug("running");

    }

}
