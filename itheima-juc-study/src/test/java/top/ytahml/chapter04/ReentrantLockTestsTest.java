package top.ytahml.chapter04;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static top.ytahml.utils.ThreadUtils.sleep;

/**
 * @author 花木凋零成兰
 * @title ReentrantLockTestsTest
 * @date 2025-07-13 22:19
 * @package top.ytahml.chapter04
 * @description
 */
@Slf4j
public class ReentrantLockTestsTest {

    private static final ReentrantLock LOCK = new ReentrantLock();

    // 测试可重入锁，可打断的特性
    @SneakyThrows
    @Test
    public void test1() {
        // 可以被打断的获取锁
        Thread t1 = new Thread(() -> {
            try {
                log.debug("尝试获取到锁 ... ");
                // 如果没有竞争，则获取到LOCK对象锁
                // 如果有竞争，则进入阻塞队列，可以被其他线程用 interrupt 方法打断
                LOCK.lockInterruptibly();
            } catch (InterruptedException e) {
                log.debug("没有获取到锁: {}", e.getMessage());
                return;
            }
            try {
                log.debug("获取到锁 ...");
            } finally {
                LOCK.unlock();
            }
        }, "t1");

        // 主线程先获取到锁
        LOCK.lock();

        t1.start();

        // 打断 t1
        sleep(2000);
        log.debug("打断t1 ...");
        t1.interrupt();


        sleep(2000);

    }

    @Test
    public void test2() {
        Thread t1 = new Thread(() -> {
            // 尝试获取锁
            log.debug("尝试获取到锁 ...");
            boolean result;
            try {
                result = LOCK.tryLock(2, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                log.debug("获取锁异常: {}", e.getMessage());
                return;
            }
            if (!result) {
                log.warn("获取不到锁 ...");
                return;
            }
            try {
                log.debug("获得锁 ...");
            } finally {
                LOCK.unlock();
            }

        }, "t1");

        // 主线程先获取到锁
        LOCK.lock();
        t1.start();
        sleep(1000);
        // 主线程1s后释放锁
        LOCK.unlock();

        sleep(5000);
    }

    // ReentrantLock 默认是不公平的，可以设置为公平

    // ReentrantLock 条件变量
    @SneakyThrows
    @Test
    public void conditionTest() {
        // 创建条件变量
        Condition condition1 = LOCK.newCondition();
        Condition condition2 = LOCK.newCondition();

        LOCK.lock();
        // 当条件1不满足时，等待
        // 需要获得锁，才能等待；可以被唤醒、打断、超时
        condition1.await();

        // 随机唤醒条件变量1的一个等待线程
        condition1.signal();
        // 唤醒条件变量1的所有等待线程
        condition1.signalAll();
    }


}