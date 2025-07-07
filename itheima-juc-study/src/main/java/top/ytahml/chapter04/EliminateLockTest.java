package top.ytahml.chapter04;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author 花木凋零成兰
 * @title EliminateLockTest
 * @date 2025-07-06 20:58
 * @package top.ytahml.chapter04
 * @description
 */
@Fork(1)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 3)
@Measurement(iterations = 5)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class EliminateLockTest {

    static int x = 0;

    @Benchmark
    public void a() {
        x++;
    }

    @Benchmark
    public void b() {
        Object o = new Object();
        synchronized (o) {
            x++;
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(EliminateLockTest.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }

}
