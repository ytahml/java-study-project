package top.ytahml.chapter04;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import top.ytahml.utils.ThreadUtils;

import static org.junit.Assert.*;

/**
 * @author 花木凋零成兰
 * @title ThreadStateTest
 * @date 2025-07-10 21:01
 * @package top.ytahml.chapter04
 * @description 线程状态
 */
@Slf4j
public class ThreadStateTest {

    final static Object LOCK = new Object();

    /**
     * 线程从RUNNABLE <--> WAITING
     * 1、调用 wait 方法； R -> W
     * 2、调用notify、notifyAll、interrupt方法；W -> R
     * 竞争锁失败后，会从 W -> BLOCKED
     */
    @Test
    public void test1() {
        new Thread(() -> {
            synchronized (LOCK) {
                log.debug("执行 ... ");
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.debug("其他代码 ... ");
            }
        }, "t1").start();

        new Thread(() -> {
            synchronized (LOCK) {
                log.debug("执行 ... ");
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.debug("其他代码 ... ");
            }
        }, "t2").start();

        ThreadUtils.sleep(2000);
        log.debug("环境 obj 上其他线程 ... ");
        synchronized (LOCK) {
            LOCK.notifyAll();
        }

        log.debug("测试主线程 ...");
    }




}