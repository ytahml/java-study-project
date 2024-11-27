package top.ytazwc.chapter02;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author 花木凋零成兰
 * @title MyBenchmark
 * @date 2024-11-26 22:45
 * @package top.ytazwc.chapter02
 * @description
 * <a href="https://www.cnblogs.com/54chensongxia/p/15485421.html">JMH 使用指南</a>
 */
@Fork(1)
@BenchmarkMode(Mode.AverageTime)    // 统计模式
@Warmup(iterations = 3) // 预热三次
@Measurement(iterations = 5)
public class MyBenchmark {

    static int[] ARRAY = new int[1000_000_00];

    static {
        Arrays.fill(ARRAY, 1);
    }

    @Benchmark
    public int c() throws ExecutionException, InterruptedException {
        int[] array = ARRAY;
        FutureTask<Integer> t1 = new FutureTask<>(() -> {
            int sum = 0;
            for (int i = 0; i < 25000000; ++ i) {
                sum += array[i];
            }
            return sum;
        });
        FutureTask<Integer> t2 = new FutureTask<>(() -> {
            int sum = 0;
            for (int i = 25000000; i < 50000000; ++ i) {
                sum += array[i];
            }
            return sum;
        });
        FutureTask<Integer> t3 = new FutureTask<>(() -> {
            int sum = 0;
            for (int i = 50000000; i < 75000000; ++ i) {
                sum += array[i];
            }
            return sum;
        });
        FutureTask<Integer> t4 = new FutureTask<>(() -> {
            int sum = 0;
            for (int i = 75000000; i < 100000000; ++ i) {
                sum += array[i];
            }
            return sum;
        });
        new Thread(t1).start();
        new Thread(t2).start();
        new Thread(t3).start();
        new Thread(t4).start();
        return t1.get() + t2.get() + t3.get() + t4.get();
    }

    @Benchmark
    public int d() throws ExecutionException, InterruptedException {
        int[] array = ARRAY;
        FutureTask<Integer> t1 = new FutureTask<>(() -> {
            int sum = 0;
            for (int i = 0; i < 100000000; i++) {
                sum += array[i];
            }
            return sum;
        });
        new Thread(t1).start();
        return t1.get();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException, RunnerException {
        Options opt = new OptionsBuilder()
                .include(MyBenchmark.class.getSimpleName())
                .result("result.json")
                .resultFormat(ResultFormatType.JSON)
                .build();
        new Runner(opt).run();
    }

}
