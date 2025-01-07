package top.ytazwc;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author 花木凋零成兰
 * @title Sample_06_FixtureLevel
 * @date 2024-12-31 22:12
 * @package top.ytazwc
 * @description
 */
@State(Scope.Thread)
@Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.MILLISECONDS)
public class Sample_06_FixtureLevel {

    int x;

    @TearDown(Level.Invocation)
    public void check() {
//        assert x > 1 : "Nothing changed?";
        System.out.println("\n--------TearDown---------\n");
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
                .include(Sample_06_FixtureLevel.class.getSimpleName())
                .forks(1)
                .jvmArgs("-ea")
                .shouldFailOnError(false)
                .build();

        new Runner(opt).run();
    }

}
