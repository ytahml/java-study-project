package top.ytahml.chapter04;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import top.ytahml.utils.ThreadUtils;

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

        Thread t1 = getThread("t1", 0);
        t1.start();

        Thread t2 = getThread("t2", 0);
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

    @Test
    public void waitApiTest() {
        Thread t1 = getThread("t1", 1000);
        t1.start();
        ThreadUtils.sleep(3000);
    }

    private static Thread getThread(String name, int timeout) {
        return new Thread(() -> {
            synchronized (LOCK) {
                log.debug("执行 ... ");
                try {
                    // 让线程等待指定时间，为0则一直等待
                    LOCK.wait(timeout);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.debug("其他代码 ...");
            }
        }, name);
    }

    /**
     * sleep(long n) 是Thread方法，wait(long n) 是Object方法
     * sleep 不需要强制和 synchronized 配合使用；wait需要
     *
     */



}