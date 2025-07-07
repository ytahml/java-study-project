package top.ytahml.chapter04;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

import java.util.Vector;

/**
 * @author 花木凋零成兰
 * @title BiasedTest3
 * @date 2025-07-06 20:24
 * @package top.ytahml.chapter04
 * @description 批量重偏向
 */
@Slf4j
public class BiasedTest3 {

    public static void main(String[] args) {

        Vector<Dog> list = new Vector<>();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                Dog dog = new Dog();
                list.add(dog);
                synchronized (dog) {
                    System.out.println(ClassLayout.parseInstance(dog).toPrintable());
                }
            }
            synchronized (list) {
                list.notify();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {

            synchronized (list) {
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            log.error("----------------------------------------");
            for (int i = 0; i < 30; i++) {
                Dog dog = list.get(i);
                System.out.println(ClassLayout.parseInstance(dog).toPrintable());
                synchronized (dog) {
                    System.out.println(ClassLayout.parseInstance(dog).toPrintable());
                }
                System.out.println(ClassLayout.parseInstance(dog).toPrintable());
            }

        }, "t2");

        t1.start();
        t2.start();
    }

    private static class Dog {

    }

}
