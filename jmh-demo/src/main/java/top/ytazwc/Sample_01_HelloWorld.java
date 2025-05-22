package top.ytazwc;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * @author 花木凋零成兰
 * @title Sample_01_HelloWorld
 * @date 2024-11-27 22:45
 * @package top.ytazwc
 * @description jmh 第一个测试用例
 */
public class Sample_01_HelloWorld {

    @Benchmark  // 需要测试的方法
    public void wellHelloThere() {
        // this method was intentionally left blank.
    }


    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Sample_01_HelloWorld.class.getSimpleName())
                // 总共测试几轮
                .forks(1)
                .build();

        new Runner(opt).run();
    }

}
