package top.ytazwc;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author 花木凋零成兰
 * @title Sample_09_Blackholes
 * @date 2024-12-31 23:24
 * @package top.ytazwc
 * @description
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
public class Sample_09_Blackholes {

    int x1;

    int x2;

    private int compute(int d) {
        for (int c = 0; c < 10; c++) {
            d = d * d / 42;
        }
        return d;
    }


    //基准方法 用于作对比
    @Benchmark
    public int baseline() {
        return compute(x1);
    }

    @Benchmark
    public int measureWrong() {
        // 没有接收返回值；JVM 可能会认为下述方法可以忽略
        compute(x1);
        return compute(x2);
    }

    @Benchmark
    public int measureRight_1() {
        // 正确做法：对两个调用的返回值进行统一接收
        return compute(x1) + compute(x2);
    }

    @Benchmark
    public void measureRight_2(Blackhole bh) {
        // 如果执行结果不使用 编译器会自动优化
        // 为了防止编译器过度优化 可以使用 JMH 提供的黑洞 Blackhole 对象对执行结果进行消费；
        bh.consume(compute(x1));
        bh.consume(compute(x2));
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Sample_09_Blackholes.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

}
