package top.ytahml.chapter08.juc.aqs;

import lombok.extern.slf4j.Slf4j;
import top.ytahml.utils.ThreadUtils;

/**
 * 请添加类的描述信息.
 *
 * @author 花木凋零成兰
 * @since 2025/10/3 下午4:31
 */
@Slf4j
public class CustomAqsTest {

    public static void main(String[] args) {
        CustomAqs lock = new CustomAqs();
        new Thread(() -> {
            lock.lock();
            try {
                log.debug("locking ...");
                ThreadUtils.sleep(2000);
            } finally {
                log.debug("unlocking ...");
                lock.unlock();
            }
        }, "t1").start();

        new Thread(() -> {
            lock.lock();
            try {
                log.debug("locking ...");
            } finally {
                log.debug("unlocking ...");
                lock.unlock();
            }
        }, "t2").start();
    }

}
