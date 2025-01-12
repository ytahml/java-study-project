package top.ytazwc;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author 花木凋零成兰
 * @title Sample_12_Forking_0
 * @date 2025-01-12 10:34
 * @package top.ytazwc
 * @description
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 1, timeUnit = TimeUnit.SECONDS)
public class Sample_12_Forking_default {

    @Benchmark
    @Fork   // 默认值为 -1；等同于值为 5
    public int measure1C1() {
        return 1;
    }

    @Setup(Level.Trial)
    public void setup() {
        printProcessId("setup");
    }

    public static void printProcessId(String name) {
        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println(name + " pid is: " + ManagementFactory.getRuntimeMXBean().getName());
        System.out.println("----------------------------------------");
        System.out.println();
    }

    public static void main(String[] args) throws RunnerException {
        printProcessId("main");
        Options opt = new OptionsBuilder()
                .include(Sample_12_Forking_default.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }

}
