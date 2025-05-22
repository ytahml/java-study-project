package top.ytazwc;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author 花木凋零成兰
 * @title Sample_05_StateFixtures
 * @date 2024-12-31 21:41
 * @package top.ytazwc
 * @description State 只的方法如何被执行
 * 简单介绍 @Setup 和 @TearDown
 */
@State(Scope.Thread)
@Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
public class Sample_05_StateFixtures {

    int x;


    @Setup
    public void prepare() {
        x = 1;
        System.out.println("\n-----------------Setup---------------\n");
    }

    @TearDown
    public void check() {
//        assert x > 1 : "Nothing changed?";
        System.out.println("\n---------------TearDown-----------------\n");
    }


    @Benchmark
    public void measureRight() {
        x++;
    }



    @Benchmark
    public void measureWrong() {
        int x = 0;
        x++;
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Sample_05_StateFixtures.class.getSimpleName())
                .forks(1)
                .jvmArgs("-ea")
                .build();

        new Runner(opt).run();
    }

}
