package top.ytahml.chapter08.juc.aqs;

import lombok.extern.slf4j.Slf4j;
import top.ytahml.utils.ThreadUtils;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁：读操作不互斥，读写互斥，写写互斥
 *
 * @author 花木凋零成兰
 * @since 2025/10/4 下午12:37
 */
@Slf4j
public class ReadWriteLockTest {

    public static void main(String[] args) {
        DataContainer<String> dataContainer = new DataContainer<>();
        new Thread(() -> {
            System.out.println(dataContainer.read());
        }, "t1").start();
        ThreadUtils.sleep(100);
        new Thread(() -> {
            dataContainer.write();
        }, "t2").start();
    }

    @Slf4j
    private static class DataContainer<T> {

        // 数据
        private T data;
        // 读写锁
        private final ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
        // 读锁
        private final ReentrantReadWriteLock.ReadLock r = rw.readLock();
        // 写锁
        private final ReentrantReadWriteLock.WriteLock w = rw.writeLock();

        public T read() {
            log.debug("get read lock ...");
            r.lock();
            try {
                log.info("read operate ...");
                ThreadUtils.sleep(1000);
                return data;
            } finally {
                log.debug("release read lock ...");
                r.unlock();
            }
        }

        public void write() {
            log.debug("get write lock ...");
            w.lock();
            try {
                log.info("write data ...");
            } finally {
                log.debug("release write lock ...");
                w.unlock();
            }
        }

    }

}
