package top.ytahml.chapter04;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

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
        WaitNotify waitNotify = new WaitNotify(1, 5);
        new Thread(() -> waitNotify.print("a", 1, 2), "a").start();
        new Thread(() -> waitNotify.print("b", 2, 3), "b").start();
        new Thread(() -> waitNotify.print("c", 3, 1), "b").start();
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
