package top.ytazwc;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author 花木凋零成兰
 * @title Sample_04_DefaultState
 * @date 2024-12-30 22:55
 * @package top.ytazwc
 * @description
 */
@State(Scope.Thread)
@Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
public class Sample_04_DefaultState {
    int x;

    @Benchmark
    public void measure() {
        x++;
    }


    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Sample_04_DefaultState.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

}
