package top.ytahml.chapter04;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author 花木凋零成兰
 * @title WaitAndNotifyTestsTest
 * @date 2025-07-07 20:13
 * @package top.ytahml.chapter04
 * @description
 */
@Slf4j
public class WaitAndNotifyTestsTest {

    static final Object LOCK = new Object();

    /**
     * 只有在获取到锁之后，才能调用 wait/notify 方法
     */
    @Test
    public void apiTest1() {
        synchronized (LOCK) {
            LOCK.notify();
        }
    }

    /**
     * notify compare notifyAll method
     */
    @Test
    public void notifyOrNotifyAll() throws InterruptedException {

        Thread t1 = new Thread(() -> {
            synchronized (LOCK) {
                log.debug("执行 ... ");
                try {
                    // 让线程一直等待
                    LOCK.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.debug("其他代码 ...");
            }
        }, "t1");
        t1.start();

        Thread t2 = new Thread(() -> {
            synchronized (LOCK) {
                log.debug("执行 ... ");
                try {
                    // 让线程一直等待
                    LOCK.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.debug("其他代码 ...");
            }
        }, "t2");
        t2.start();

        // 主线程两秒之后唤醒
        Thread.sleep(2000);
        log.debug("唤醒 LOCK 上其他线程");
        synchronized (LOCK) {
            // 随机唤醒一个线程
//            LOCK.notify();
            // 唤醒等待的所有线程
            LOCK.notifyAll();
        }

    }


}