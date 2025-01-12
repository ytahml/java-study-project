package top.ytazwc;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.concurrent.TimeUnit;

/**
 * @author 花木凋零成兰
 * @title Sample_13_RunToRun
 * @date 2025-01-12 12:59
 * @package top.ytazwc
 * @description
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class Sample_13_RunToRun {

    @State(Scope.Thread)
    public static class SleepyState {
        public long sleepTime;

        @Setup
        public void setup() {
            sleepTime = (long) (Math.random() * 1000);
            System.out.println("sleepTime = " + sleepTime);
        }
    }

    @Benchmark
    @Fork(1)
    public void baseline(SleepyState s) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(s.sleepTime);
    }

    @Benchmark
    @Fork(5)
    public void fork_1(SleepyState s) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(s.sleepTime);
    }

    @Benchmark
    @Fork(20)
    public void fork_2(SleepyState s) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(s.sleepTime);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Sample_13_RunToRun.class.getSimpleName())
                .warmupIterations(0)
                .measurementIterations(1)
                .measurementTime(TimeValue.seconds(5))
                .build();

        new Runner(opt).run();
    }

}
