package top.ytazwc;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author 花木凋零成兰
 * @title Sample_02_BenchmarkModes
 * @date 2024-11-27 22:59
 * @package top.ytazwc
 * @description JMH 官方第二个示例
 */
@Warmup(iterations = 1, time = 1)   // 预测次数和时间
@Measurement(iterations = 1, time = 1)  // 测试次数和时间
public class Sample_02_BenchmarkModes {

    @Benchmark
    @BenchmarkMode(Mode.Throughput) // 设置测试标准模式，按照吞吐量测试；输出单位：每单位时间该方法会执行次数
    @OutputTimeUnit(TimeUnit.DAYS)   // 输出报告的时间单位
    public void measureThroughput() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100);
    }


    @Benchmark
    @BenchmarkMode(Mode.AverageTime)    // 测试标准为：平均耗时测试，输出报告：每次操作耗时
    @OutputTimeUnit(TimeUnit.SECONDS)  // 耗时的时间单位
    public void measureAvgTime() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100);
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)     // 测试模式：抽象测试，输出报告：会在执行过程中采样(每次操作耗时)，最快的，50%快的，90%，95%，99%，99.9%，100%
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void measureSamples() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100);
    }


    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime) // 冷启动测试，设置了之后，该方法在一轮中只会运行一次，该模式主要是为了测试冷启动的性能
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void measureSingleShot() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100);
    }


    @Benchmark
    @BenchmarkMode({Mode.Throughput, Mode.AverageTime, Mode.SampleTime, Mode.SingleShotTime})   // 四种测试模式都会运行 并输出测试报告
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void measureMultiple() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100);
    }

    @Benchmark
    @BenchmarkMode(Mode.All)    // 同理，四种测试模式都会运行 并输出测试报告
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void measureAll() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Sample_02_BenchmarkModes.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

}
