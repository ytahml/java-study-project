package top.ytahml.chapter04;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * @author 花木凋零成兰
 * @title BiasedTest
 * @date 2025-07-06 16:36
 * @package top.ytahml.chapter04
 * @description 偏向锁测试
 */
@Slf4j
public class BiasedTest {

    @SneakyThrows
    public static void main(String[] args) {
        // 查看对象对象头
        Dog dog = new Dog();
        System.out.println(VM.current().details());
        System.out.println(ClassLayout.parseInstance(dog).toPrintable());

        Thread.sleep(4000);
        System.out.println(ClassLayout.parseInstance(new Dog()).toPrintable());

    }

}

class Dog {

}