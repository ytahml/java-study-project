package top.ytazwc;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author 花木凋零成兰
 * @title Sample_10_ConstantFold
 * @date 2025-01-07 22:47
 * @package top.ytazwc
 * @description 常量折叠示例
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
public class Sample_10_ConstantFold {

    private int x = 42;

    private final int wrongX = 42;

    private int compute(int d) {
        for (int c = 0; c < 10; c++) {
            d = d * d / 42;
        }
        return d;
    }

    @Benchmark
    public int baseline() {
        // 作为标准的示例
        return 42;
    }

    @Benchmark
    public int measureWrong_1() {
        // 错误示例1：常量进行折叠，会在编译期直接获得结果；会将 compute(42) 直接优化为一个结果进行返回
        return compute(42);
    }

    @Benchmark
    public int measureWrong_2() {
        // 错误示例2：虽然是成员变量 但是用 final 修饰，依然会被编译器进行优化
        return compute(wrongX);
    }

    @Benchmark
    public int measureRight() {
        return compute(x);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Sample_10_ConstantFold.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

}
