package top.ytahml.chapter04;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

import java.util.Vector;
import java.util.concurrent.locks.LockSupport;

/**
 * @author 花木凋零成兰
 * @title BiasedTest4
 * @date 2025-07-06 20:40
 * @package top.ytahml.chapter04
 * @description 批量撤销，会变成不可偏向
 */
@Slf4j
public class BiasedTest4 {

    static Thread t1,t2,t3;
    public static void main(String[] args) throws InterruptedException {

        Vector<Dog> list = new Vector<>();
        int loopNumber = 39;
        t1 = new Thread(() -> {
            for (int i = 0; i < loopNumber; i++) {
                Dog d = new Dog();
                list.add(d);
                synchronized (d) {
                    System.out.println(ClassLayout.parseInstance(d).toPrintable());
                }
            }
            LockSupport.unpark(t2);
        }, "t1");
        t1.start();
        t2 = new Thread(() -> {
            LockSupport.park();
            log.debug("===============> ");
            for (int i = 0; i < loopNumber; i++) {
                Dog d = list.get(i);
                System.out.println(ClassLayout.parseInstance(d).toPrintable());
                synchronized (d) {
                    System.out.println(ClassLayout.parseInstance(d).toPrintable());
                }
                System.out.println(ClassLayout.parseInstance(d).toPrintable());
            }
            LockSupport.unpark(t3);
        }, "t2");
        t2.start();
        t3 = new Thread(() -> {
            LockSupport.park();
            log.debug("===============> ");
            for (int i = 0; i < loopNumber; i++) {
                Dog d = list.get(i);
                System.out.println(ClassLayout.parseInstance(d).toPrintable());
                synchronized (d) {
                    System.out.println(ClassLayout.parseInstance(d).toPrintable());
                }
                System.out.println(ClassLayout.parseInstance(d).toPrintable());
            }
        }, "t3");
        t3.start();
        t3.join();
        log.debug(ClassLayout.parseInstance(new Dog()).toPrintable());

    }

}
