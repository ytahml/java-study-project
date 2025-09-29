package top.ytahml.chapter06.juc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 原子引用数组.
 *
 * @author 花木凋零成兰
 * @since 2025/9/29 下午2:06
 */
public class ArrayAtomicTest {


    public static void main(String[] args) {
        demo(
                () -> new int[10],
                a -> a.length,
                (array, index) -> array[index] ++,
                a -> System.out.println(Arrays.toString(a))
        );
        // 采用原子数组
        demo(
                () -> new AtomicIntegerArray(10),
                AtomicIntegerArray::length,
                AtomicIntegerArray::getAndIncrement,
                System.out::println
        );
    }

    /**
     * 参数1：提供数组、可以是线程不安全或线程安全数组
     * 参数2：获取数组长度的方法
     * 参数3：自增方法，回传 array、index
     * 参数4：打印数组的方法
     */
    private static <T> void demo(
            Supplier<T> arraySupplier,
            Function<T, Integer> lengthFun,
            BiConsumer<T, Integer> putConsumer,
            Consumer<T> printConsumer
    ) {
        List<Thread> ts = new ArrayList<>();
        T array = arraySupplier.get();
        Integer length = lengthFun.apply(array);
        for (int i = 0; i < length; i++) {
            // 每个线程对数组作 10000 次操作
            ts.add(new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    putConsumer.accept(array, j % length);
                }
            }));
        }
        ts.forEach(Thread::start);
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        printConsumer.accept(array);
    }

}
