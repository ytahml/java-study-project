package top.ytahml.chapter08.juc;

import lombok.extern.slf4j.Slf4j;
import top.ytahml.utils.ThreadUtils;

import java.util.concurrent.SynchronousQueue;

/**
 * 测试交换队列；一手交钱一手交货；没有来取任务的线程2，则线程1无法将任务放入线程
 *
 * @author 花木凋零成兰
 * @since 2025/10/1 上午10:54
 */
@Slf4j
public class SynchronousQueueTest {

    public static void main(String[] args) {
        SynchronousQueue<Integer> queue = new SynchronousQueue<>();
        new Thread(() -> {
            try {
                log.debug("putting: {}", 1);
                queue.put(1);
                log.debug("putted: {}", 1);
                log.debug("putting: {}", 2);
                queue.put(2);
                log.debug("putted: {}", 2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "t1").start();

        ThreadUtils.sleep(1000);

        new Thread(() -> {
            try {
                log.debug("taking: {}", 1);
                queue.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "t2").start();

        ThreadUtils.sleep(1000);

        new Thread(() -> {
            try {
                log.debug("taking: {}", 2);
                queue.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "t3").start();
    }

}
