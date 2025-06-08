package top.ytahml.chapter04;

import lombok.extern.slf4j.Slf4j;
import top.ytahml.utils.ThreadUtils;

/**
 * @author 花木凋零成兰
 * @title SynTest
 * @date 2025-06-08 17:05
 * @package top.ytahml.chapter04
 * @description 测试线程八锁
 */
@Slf4j
public class SynTest {

    public static void main(String[] args) {

        Number n1 = new Number();
        Number n2 = new Number();

        new Thread(() -> {
            log.debug("begin ...");
            n1.a();
        }).start();

        new Thread(() -> {
            log.debug("begin ...");
            n2.b();
        }).start();
//
//        new Thread(() -> {
//            log.debug("begin ...");
//            n1.c();
//        }).start();

    }

}

@Slf4j
class Number {

    public synchronized void a() {
        ThreadUtils.sleep(1000);
        log.debug("1");
    }

    public synchronized void b() {
        log.debug("2");
    }

}
