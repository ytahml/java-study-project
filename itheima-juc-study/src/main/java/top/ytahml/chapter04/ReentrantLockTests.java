package top.ytahml.chapter04;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 花木凋零成兰
 * @title ReentrantLockTests
 * @date 2025-07-13 22:14
 * @package top.ytahml.chapter04
 * @description 可重入锁
 */
@Slf4j
public class ReentrantLockTests {

    private static final ReentrantLock LOCK = new ReentrantLock();

    public static void main(String[] args) {

        LOCK.lock();
        try {
            log.debug("enter main ...");
            method1();
        } finally {
            LOCK.unlock();
        }

    }

    public static void method1() {
        LOCK.lock();
        try {
            log.debug("enter method1 ...");
            method2();
        } finally {
            LOCK.unlock();
        }
    }

    public static void method2() {
        LOCK.lock();
        try {
            log.debug("enter method2 ...");
        } finally {
            LOCK.unlock();
        }
    }

}
