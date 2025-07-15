package top.ytahml.chapter04;

import lombok.extern.slf4j.Slf4j;
import top.ytahml.utils.ThreadUtils;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 花木凋零成兰
 * @title WaitAndNotifyTests
 * @date 2025-07-07 20:12
 * @package top.ytahml.chapter04
 * @description wait 和 notify 的正确使用姿势
 */
@Slf4j
public class WaitAndNotifyTests {

    /**
     * 正确使用姿势:
     * synchronized(lock) {
     *    while(条件不成立) {
     *        lock.wait();
     *    }
     * // 干活
     * }
     * //另一个线程
     * synchronized(lock) {
     *     lock.notifyAll();
     * }
     */

    // 房间
    static final Object ROOM = new Object();
    // 是否有🚬
    static boolean hasCigarette = false;
    static boolean hasTakeout = false;

    static ReentrantLock LOCK_ROOM = new ReentrantLock();
    // 等待烟的条件
    static Condition waitCigarette = LOCK_ROOM.newCondition();
    // 等待外卖的条件
    static Condition waitTakeout = LOCK_ROOM.newCondition();

    public static void main(String[] args) {

        new Thread(() -> {
//            synchronized (ROOM) {
//                log.debug("外卖送到没? [{}]", hasTakeout);
//                while (!hasTakeout) {
//                    log.debug("没外卖, 先歇会!");
////                    ThreadUtils.sleep(2000);
//                    try {
//                        ROOM.wait();
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//                log.debug("外卖送到没? [{}]", hasTakeout);
//                if (hasTakeout) {
//                    log.debug("可以开始干活了");
//                } else {
//                    log.debug("没干成活!");
//                }
//            }
            // 使用ReentrantLock加锁
            LOCK_ROOM.lock();
            try {
                log.debug("外卖送到没? [{}]", hasTakeout);
                while (!hasTakeout) {
                    log.debug("没外卖, 先歇会!");
                    try {
                        waitTakeout.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                log.debug("可以开始干活了");
            } finally {
                LOCK_ROOM.unlock();
            }
        }, "小兰").start();

        new Thread(() -> {
//            synchronized (ROOM) {
//                log.debug("有烟没? [{}]", hasCigarette);
//                while (!hasCigarette) {
//                    log.debug("没烟, 先歇会!");
////                    ThreadUtils.sleep(2000);
//                    try {
//                        ROOM.wait();
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//                log.debug("有烟没? [{}]", hasCigarette);
//                if (hasCigarette) {
//                    log.debug("可以开始干活了");
//                }
//            }
            LOCK_ROOM.lock();
            try {
                log.debug("有烟没? [{}]", hasCigarette);
                while (!hasCigarette) {
                    log.debug("没烟, 先歇会!");
                    try {
                        waitCigarette.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                log.debug("可以开始干活了");
            } finally {
                LOCK_ROOM.unlock();
            }
        }, "小南").start();

//        for (int i = 0; i < 5; ++ i) {
//            new Thread(() -> {
//                synchronized (ROOM) {
//                    log.debug("可以开始干活了");
//                }
//            }, "其他人").start();
//        }

        ThreadUtils.sleep(1000);
        new Thread(() -> {
            LOCK_ROOM.lock();
            try {
                // 条件满足：即外卖送到了
                hasTakeout = true;
                waitTakeout.signal();
            } finally {
                LOCK_ROOM.unlock();
            }
        }, "送外卖的").start();

        ThreadUtils.sleep(1000);
        new Thread(() -> {
            LOCK_ROOM.lock();
            try {
                // 条件满足：即烟送到了
                hasCigarette = true;
                waitCigarette.signal();
            } finally {
                LOCK_ROOM.unlock();
            }
        }, "送烟的").start();

    }

}
