package top.ytahml.chapter06.juc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 原子累加器
 *
 * @author 花木凋零成兰
 * @since 2025/9/29 下午2:51
 */
public class IncrementAtomicTest {

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            demo(
                    () -> new AtomicLong(0),
                    AtomicLong::getAndIncrement
            );
        }

        System.out.println();

        for (int i = 0; i < 10; i++) {
            demo(
                    LongAdder::new,
                    LongAdder::increment
            );
        }



    }

    private static <T> void demo(Supplier<T> adderSupplier, Consumer<T> action) {
        T adder = adderSupplier.get();
        List<Thread> ts = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ts.add(new Thread(() -> {
                for (int j = 0; j < 500000; j++) {
                    action.accept(adder);
                }
            }));
        }
        long start = System.nanoTime();
        ts.forEach(Thread::start);
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        long end = System.nanoTime();
        System.out.println(adder + " cost: " + (end - start) / 1000_000);
    }

}
