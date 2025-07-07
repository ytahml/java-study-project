package top.ytahml.chapter04;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * @author 花木凋零成兰
 * @title BiasedTest
 * @date 2025-07-06 16:36
 * @package top.ytahml.chapter04
 * @description 偏向锁测试
 * -XX:BiasedLockingStartupDelay=0 偏向锁加载延迟为0
 */
@Slf4j
public class BiasedTest {

    @SneakyThrows
    public static void main(String[] args) {
//        Thread.sleep(5000);
        Dog dog = new Dog();

        // 调用hashcode方法会清除偏向锁
        System.out.println(dog.hashCode());

        System.out.println("【初始对象头】");
        System.out.println(ClassLayout.parseInstance(dog).toPrintable());

        synchronized (dog) {
            System.out.println("【首次加锁后（期望偏向锁）】");
            System.out.println(ClassLayout.parseInstance(dog).toPrintable());
        }

        System.out.println("【释放锁后】");
        System.out.println(ClassLayout.parseInstance(dog).toPrintable());

    }

}

class Dog {

}