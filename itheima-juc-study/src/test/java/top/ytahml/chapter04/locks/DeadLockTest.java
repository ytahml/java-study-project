package top.ytahml.chapter04.locks;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static top.ytahml.utils.ThreadUtils.sleep;

/**
 * @author 花木凋零成兰
 * @title DeadLockTest
 * @date 2025-07-13 15:01
 * @package top.ytahml.chapter04.locks
 * @description 死锁测试
 */
@Slf4j
public class DeadLockTest {

    final Object A = new Object();

    final Object B = new Object();

    @Test
    public void test1() {

        Thread t1 = new Thread(() -> {
            synchronized (A) {
                log.debug("Lock A ...");
                sleep(1000);
                synchronized (B) {
                    log.debug("Lock B ...");
                    log.debug("操作 ...");
                }
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            synchronized (B) {
                log.debug("Lock B ...");
                sleep(1000);
                synchronized (A) {
                    log.debug("Lock A ...");
                    log.debug("操作 ...");
                }
            }
        }, "t2");

        t1.start();
        t2.start();

        sleep(3000);

    }

}