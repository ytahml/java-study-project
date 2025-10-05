package top.ytahml.chapter08.juc;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import top.ytahml.utils.ThreadUtils;

import java.util.concurrent.locks.StampedLock;

/**
 * StampLock读写锁，性能更好，需要配合戳(stamp)使用
 * 1、读读并发，读写互斥
 * 2、不支持条件变量
 * 3、不支持可重入
 * @author 花木凋零成兰
 * @since 2025/10/5 下午1:03
 */
@Slf4j
public class StampLockTest {

    public static void main(String[] args) {
        DataContainerStamped container = new DataContainerStamped(1);
        new Thread(() -> {
            int read = container.read(1000);
            System.out.println(read);
        }, "t1").start();
        ThreadUtils.sleep(500);
        new Thread(() -> {
//            int read = container.read(0);
//            System.out.println(read);
            container.write(1000);
        }, "t2").start();
    }

    @Slf4j
    @Getter
    private static class DataContainerStamped {

        private int data;
        private final StampedLock lock = new StampedLock();

        public DataContainerStamped(int data) {
            this.data = data;
        }

        // 入参：读取将来耗费的时间
        private int read(int readTime) {
            long stamp = lock.tryOptimisticRead();
            log.info("optimistic read lock {}", stamp);
            ThreadUtils.sleep(readTime);
            if (lock.validate(stamp)) {
                log.debug("read finish {}", stamp);
                return data;
            }
            // 锁升级，从乐观读锁升级为读锁
            log.warn("rank up read lock: {}", stamp);
            try {
                stamp = lock.readLock();
                log.debug("read data: {}", data);
                ThreadUtils.sleep(readTime);
                log.debug("read finish ...");
                return data;
            } finally {
                log.info("read unlock {}", stamp);
                lock.unlockRead(stamp);
            }
        }

        public void write(int newData) {
            long stamp = lock.writeLock();
            log.info("write lock {}", stamp);
            try {
                log.debug("write data: {}", newData);
                ThreadUtils.sleep(2000);
                this.data = newData;
            } finally {
                log.info("write unlock {}", stamp);
                lock.unlock(stamp);
            }
        }

    }

}
