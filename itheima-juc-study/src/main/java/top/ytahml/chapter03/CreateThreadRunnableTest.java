package top.ytahml.chapter03;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 花木凋零成兰
 * @title CreateThreadRunnableTest
 * @date 2025-05-24 17:48
 * @package top.ytahml.chapter03
 * @description 测试实现Runnable对象创建线程
 */
@Slf4j(topic = "CreateThreadRunnableTest")
public class CreateThreadRunnableTest {

    public static void main(String[] args) {

        Runnable r = new Runnable() {
            @Override
            public void run() {
                log.debug("running");
            }
        };

        Thread t = new Thread(r, "t2");
        t.start();

    }

}
