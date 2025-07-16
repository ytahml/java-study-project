package top.ytahml.chapter04;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import top.ytahml.utils.ThreadUtils;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 花木凋零成兰
 * @title AlternateOutput
 * @date 2025-07-15 22:03
 * @package top.ytahml.chapter04
 * @description 交替输出：线程 a,b,c 交替输出
 */
@Slf4j
public class AlternateOutput {

    public static void main(String[] args) {
//        WaitNotify waitNotify = new WaitNotify(1, 5);
//        new Thread(() -> waitNotify.print("a", 1, 2), "a").start();
//        new Thread(() -> waitNotify.print("b", 2, 3), "b").start();
//        new Thread(() -> waitNotify.print("c", 3, 1), "b").start();

        AwaitSignal awaitSignal = new AwaitSignal(5);
        Condition a = awaitSignal.newCondition();
        Condition b = awaitSignal.newCondition();
        Condition c = awaitSignal.newCondition();
        new Thread(() -> awaitSignal.print("a", a, b), "a").start();
        new Thread(() -> awaitSignal.print("b", b, c), "b").start();
        new Thread(() -> awaitSignal.print("c", c, a), "b").start();
        // 先唤醒a
        ThreadUtils.sleep(100);
        awaitSignal.lock();
        try {
            log.debug("开始打印");
            a.signal();
        } finally {
            awaitSignal.unlock();
        }
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    private static class AwaitSignal extends ReentrantLock {
        private int loopNumber;

        public AwaitSignal(int loopNumber) {
            this.loopNumber = loopNumber;
        }

        /**
         * 循环打印
         * @param str 打印内容
         * @param current 条件
         * @param next 下一个满足的条件
         */
        public void print(String str, Condition current, Condition next) {
            for (int i = 0; i < loopNumber; i++) {
                lock();
                try {
                    current.await();
                    System.out.println(str);
                    next.signal();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    unlock();
                }
            }
        }
    }

    @Data
    @AllArgsConstructor
    private static class WaitNotify {
        private int flag;
        private int loopNumber;

        public void print(String str, int waitFlag, int nextFlag) {
            for (int i = 0; i < loopNumber; i++) {
                synchronized (this) {
                    while (flag != waitFlag) {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.println(str);
                    flag = nextFlag;
                    this.notifyAll();
                }
            }
        }

    }

}
