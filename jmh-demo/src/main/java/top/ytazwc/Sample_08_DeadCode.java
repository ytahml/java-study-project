package top.ytazwc;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author 花木凋零成兰
 * @title Sample_08_DeadCode
 * @date 2024-12-31 22:50
 * @package top.ytazwc
 * @description 测试JVM的激进优化；方法返回值没有接受时，会被优化掉
 * 测试方法时需要注意返回值的问题；
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
public class Sample_08_DeadCode {

    int x;

    private int compute(int d) {
        for (int c = 0; c < 10; c++) {
            d = d * d / 42;
        }
        return d;
    }

    @Benchmark
    public void baseline() {
        // Do nothing, this is a baseline
    }

    @Benchmark
    public void measureWrong() {
        // 因为没有使用过 compute 方法的返回值；所以JVM会把这段代码优化掉，相当于测试了一个空方法；
        compute(x);
    }

    @Benchmark
    public int measureRight() {
        // This is correct: the result is being used.
        return compute(x);
    }


    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Sample_08_DeadCode.class.getSimpleName())
                .forks(1)
//                .jvmArgs("-server")
                .build();

        new Runner(opt).run();
    }

}
