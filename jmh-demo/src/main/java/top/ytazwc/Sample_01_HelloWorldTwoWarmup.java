package top.ytazwc;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author 花木凋零成兰
 * @title Sample_01_HelloWorld
 * @date 2024-11-27 22:45
 * @package top.ytazwc
 * @description jmh 选择预热次数和时间, 测试次数和时间
 * 在JVM之中预热之后，再进行测试
 */
@Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)    // 预热次数和时间单位
@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS) // 测试次数和时间
public class Sample_01_HelloWorldTwoWarmup {

    @Benchmark  // 需要测试的方法
    public void wellHelloThere() {
        // this method was intentionally left blank.
    }


    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Sample_01_HelloWorldTwoWarmup.class.getSimpleName())
                // 总共测试几轮
                .forks(1)
                .build();

        new Runner(opt).run();
    }

}
