package top.ytahml.chapter08.juc;

import lombok.extern.slf4j.Slf4j;
import top.ytahml.utils.ThreadUtils;

import java.util.concurrent.Semaphore;

/**
 * Semaphore 信号量：用来限制能同时访问共享资源的线程上限
 *
 * @author 花木凋零成兰
 * @since 2025/10/5 下午1:25
 */
@Slf4j
public class SemaphoreTest {

    public static void main(String[] args) {

        // 1、创建 Semaphore 对象；
        Semaphore semaphore = new Semaphore(3);
        // 2、10个线程同时运行
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                // 3、获得许可
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                }
                try {
                    log.debug("running start ...");
                    ThreadUtils.sleep(1000);
                    log.debug("running end ...");
                } finally {
                    semaphore.release();
                }

            }).start();
        }

    }

}
