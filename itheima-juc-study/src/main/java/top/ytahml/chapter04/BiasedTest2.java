package top.ytahml.chapter04;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * @author 花木凋零成兰
 * @title BiasedTest2
 * @date 2025-07-06 20:12
 * @package top.ytahml.chapter04
 * @description 其他线程同样交错使用共享变量，会从偏向锁升级为轻量级锁
 */
@Slf4j
public class BiasedTest2 {

    public static void main(String[] args) {
        Dog d = new Dog();

        Class<BiasedTest2> biasedTest2Class = BiasedTest2.class;
        Thread t1 = new Thread(() -> {
            System.out.println(ClassLayout.parseInstance(d).toPrintable());
            synchronized (d) {
                System.out.println(ClassLayout.parseInstance(d).toPrintable());
            }
            System.out.println(ClassLayout.parseInstance(d).toPrintable());

            synchronized (biasedTest2Class) {
                biasedTest2Class.notify();
            }

        }, "t1");

        Thread t2 = new Thread(() -> {
            // 与 t1 线程错开访问 d 对象
            synchronized (biasedTest2Class) {
                try {
                    biasedTest2Class.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            System.out.println(ClassLayout.parseInstance(d).toPrintable());
            synchronized (d) {
                System.out.println(ClassLayout.parseInstance(d).toPrintable());
            }
            System.out.println(ClassLayout.parseInstance(d).toPrintable());
        }, "t2");

        t1.start();
        t2.start();

    }

    private static class Dog {

    }

}
