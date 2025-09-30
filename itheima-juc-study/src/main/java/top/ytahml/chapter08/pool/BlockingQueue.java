package top.ytahml.chapter08.pool;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 阻塞队列
 *
 * @author 花木凋零成兰
 * @since 2025/9/30 下午9:01
 */
@Slf4j
public class BlockingQueue<T> {

    // 1、任务队列
    private final Deque<T> queue = new ArrayDeque<>();
    // 2、锁
    private final ReentrantLock lock = new ReentrantLock();
    // 3、生产者条件变量
    private final Condition fullWaitSet  = lock.newCondition();
    // 4、消费者条件变量
    private final Condition emptyWaitSet = lock.newCondition();
    // 5、容量
    @Getter
    @Setter
    private int capacity;

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    // 有效时间获取
    public T poll(long timeout, TimeUnit unit) {
        lock.lock();
        try {
            // 超时时间统一转化为纳秒
            long nanos = unit.toNanos(timeout);
            while (queue.isEmpty()) {
                try {
                    if (nanos <= 0) {
                        // 等待时间内没有获取到，则直接返回null
                        return null;
                    }
                    // 返回结果是剩余等待时间，被虚假唤醒后继续等待剩余时间
                    nanos = emptyWaitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            T t = queue.removeFirst();
            // 唤醒等待的生产者
            fullWaitSet.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }

    // 阻塞获取
    public T take() {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                try {
                    emptyWaitSet.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            T t = queue.removeFirst();
            // 唤醒等待的生产者
            fullWaitSet.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }

    // 阻塞添加
    public void put(T element) {
        lock.lock();
        try {
            while (queue.size() == capacity) {
                try {
                    log.warn("task wait join queue: {}", element);
                    fullWaitSet.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            log.info("task join queue: {}", element);
            queue.addLast(element);
            // 唤醒等待的消费者
            emptyWaitSet.signal();
        } finally {
            lock.unlock();
        }
    }

    public boolean offer(T element, long timeout, TimeUnit unit) {
        lock.lock();
        try {
            // 超时时间统一转化为纳秒
            long nanos = unit.toNanos(timeout);
            while (queue.size() == capacity) {
                try {
                    log.warn("task wait join queue: {}", element);
                    if (nanos <= 0) {
                        // 添加失败
                        log.error("task join queue fail: {}", element);
                        return false;
                    }
                    nanos = fullWaitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            log.info("task join queue: {}", element);
            queue.addLast(element);
            // 唤醒等待的消费者
            emptyWaitSet.signal();
            return true;
        } finally {
            lock.unlock();
        }
    }

    // 获取大小
    public int size() {
        lock.lock();
        try {
            return queue.size();
        } finally {
            lock.unlock();
        }
    }

    public void tryPut(RejectPolicy<T> rejectPolicy, T task) {
        lock.lock();
        try {
            // 判断队列是否已满
            if (queue.size() == capacity) {
                rejectPolicy.reject(this, task);
            } else {
                // 队列有空闲
                log.info("task join queue: {}", task);
                queue.addLast(task);
                // 唤醒等待的消费者
                emptyWaitSet.signal();
            }
        } finally {
            lock.unlock();
        }
    }
}
