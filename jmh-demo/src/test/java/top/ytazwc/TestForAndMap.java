package top.ytazwc;

import cn.hutool.core.util.RandomUtil;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import top.ytazwc.for_map.MatchEnum;

import java.util.concurrent.TimeUnit;

/**
 * @author 花木凋零成兰
 * @title TestForAndMap
 * @date 2025-01-12 16:44
 * @package top.ytazwc
 * @description 测试map获取值更快还是 for 循环获取对应值更快
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 1, time = 1)
@Measurement(iterations = 1, time = 1)
public class TestForAndMap {

    int value = -1;

    @Setup(Level.Invocation)
    public void prepareValue() {
        value = RandomUtil.randomInt(1, 4);
    }

    @Benchmark
    public void matchByFor(Blackhole blackhole) {
        blackhole.consume(MatchEnum.matchWithFor(value));
    }

    @Benchmark
    public void matchByMap(Blackhole blackhole) {
        blackhole.consume(MatchEnum.matchWithMap(value));
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(TestForAndMap.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }


}
