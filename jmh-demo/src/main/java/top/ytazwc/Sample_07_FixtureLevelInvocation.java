package top.ytazwc;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.*;

/**
 * @author 花木凋零成兰
 * @title Sample_07_FixtureLevelInvocation
 * @date 2024-12-31 22:27
 * @package top.ytazwc
 * @description
 */
@Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class Sample_07_FixtureLevelInvocation {

    @State(Scope.Benchmark)
    public static class NormalState {

        ExecutorService service;

        @Setup(Level.Trial) // 全局只执行一次
        public void up() {
            service = Executors.newCachedThreadPool();
        }

        @TearDown(Level.Trial)
        public void down() {
            service.shutdown();
        }

    }

    public static class LaggingState extends NormalState {

        public static final int SLEEP_TIME = Integer.getInteger("sleepTime", 10);

        // 此处模拟线程池冷启动 即测试线程池刚接到任务时 如何执行
        @Setup(Level.Invocation)    // 每次调用测试方法 之前执行该操作
        public void lag() throws InterruptedException {
            // 每次测试方法调用之前 睡眠 10 毫秒
            TimeUnit.MILLISECONDS.sleep(SLEEP_TIME);
        }

    }

    @Benchmark
    public int measureHot(NormalState e, final Scratch s) throws ExecutionException, InterruptedException {
        return e.service.submit(new Task(s)).get();
    }

    // 该方法每次调用前都会 睡眠 10毫秒
    @Benchmark
    public int measureCold(LaggingState e, final Scratch s) throws ExecutionException, InterruptedException {
        return e.service.submit(new Task(s)).get();
    }

    @State(Scope.Thread)
    public static class Scratch {
        private int p;

        public int doWork() {
            return p++;
        }

    }

    public static class Task implements Callable<Integer> {

        private final Scratch s;

        public Task(Scratch s) {
            this.s = s;
        }

        @Override
        public Integer call() {
            return s.doWork();
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Sample_07_FixtureLevelInvocation.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

}
