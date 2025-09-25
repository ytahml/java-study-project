package top.ytahml.chapter06.juc;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

/**
 * 基础类型原子类测试
 * @author 花木凋零成兰
 * @since 2025/9/25 下午9:55
 */
public class BaseTypeAtomicTest {

    public static void main(String[] args) {
        AtomicInteger i = new AtomicInteger(0);
        // 自增并获取对应值 ++i
        System.out.println(i.incrementAndGet());
        // 先获取再自增 --i
        System.out.println(i.getAndIncrement());
        System.out.println(i.get());
        // 自增指定值再获取
        System.out.println(i.addAndGet(5));
        // 先获取再自增指定值
        System.out.println(i.getAndAdd(5));
        System.out.println(i.get());

        // 原子乘法
//        System.out.println(i.updateAndGet(value -> value * 10));
        System.out.println(updateAndGet(i, value -> value / 2));

    }

    public static Integer updateAndGet(AtomicInteger i, IntUnaryOperator operator) {
        // 自己实现原子操作
        while (true) {
            int prev = i.get();
            int next = operator.applyAsInt(prev);
            if (i.compareAndSet(prev, next)) {
                return next;
            }
        }
    }

}
