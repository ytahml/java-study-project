package top.ytahml.chapter08.juc.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 自定义锁：不可重入锁
 *
 * @author 花木凋零成兰
 * @since 2025/10/3 下午3:29
 */
public class CustomAqs implements Lock {

    private MyAqs aqs = new MyAqs();

    // 自定义同步器类，独占锁
    private static class MyAqs extends AbstractQueuedSynchronizer {

        // 获取锁
        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0, 1)) {
                // 设置持锁标记
                setExclusiveOwnerThread(Thread.currentThread());
                // 加锁成功
                return true;
            }
            return false;
        }

        // 释放锁
        @Override
        protected boolean tryRelease(int arg) {
            setExclusiveOwnerThread(null);
            // state 有用volatile修饰，保证在这之前的写操作都同步到内存中
            setState(0);
            return true;
        }

        // 是否持有独占锁
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        public Condition newCondition() {
            return new ConditionObject();
        }
    }

    // 加锁，不成功进入队列等待
    @Override
    public void lock() {
        aqs.acquire(1);
    }

    // 加锁，可打断
    @Override
    public void lockInterruptibly() throws InterruptedException {
        aqs.acquireInterruptibly(1);
    }

    // 尝试加锁（只尝试一次）
    @Override
    public boolean tryLock() {
        return aqs.tryAcquire(1);
    }

    // 超时尝试加锁
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return aqs.tryAcquireSharedNanos(1, unit.toNanos(time));
    }

    // 解锁
    @Override
    public void unlock() {
        aqs.release(1);
    }

    // 创建条件变量
    @Override
    public Condition newCondition() {
        return aqs.newCondition();
    }
}
