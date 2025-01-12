package top.ytazwc;

import cn.hutool.core.util.RandomUtil;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import top.ytazwc.sort.*;

import java.util.concurrent.TimeUnit;

/**
 * @author 花木凋零成兰
 * @title TestSort
 * @date 2025-01-12 14:03
 * @package top.ytazwc
 * @description 测试十大排序
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 1, time = 1)
@Measurement(iterations = 1, time = 1)
public class TestSort {

    int[] arr;

    @Setup(Level.Invocation)
    public void prepareArray() {
        arr = new int[1000];
        for (int i = 0; i < 1000; i++) {
            arr[i] = RandomUtil.randomInt(0, 1000);
        }
    }

    @Benchmark
    public void bubbleSort(Blackhole blackhole) throws Exception {
        blackhole.consume(BubbleSort.sort(arr));
    }

    @Benchmark
    public void selectionSort(Blackhole blackhole) throws Exception {
        blackhole.consume(SelectionSort.sort(arr));
    }

    @Benchmark
    public void insertSort(Blackhole blackhole) throws Exception {
        blackhole.consume(InsertSort.sort(arr));
    }

    @Benchmark
    public void shellSort(Blackhole blackhole) throws Exception {
        blackhole.consume(ShellSort.sort(arr));
    }

    @Benchmark
    public void mergeSort(Blackhole blackhole) throws Exception {
        blackhole.consume(MergeSort.sort(arr));
    }

    @Benchmark
    public void quickSort(Blackhole blackhole) throws Exception {
        blackhole.consume(QuickSort.sort(arr));
    }

    @Benchmark
    public void heapSort(Blackhole blackhole) throws Exception {
        blackhole.consume(HeapSort.sort(arr));
    }

    @Benchmark
    public void countingSort(Blackhole blackhole) throws Exception {
        blackhole.consume(CountingSort.sort(arr));
    }

    @Benchmark
    public void bucketSort(Blackhole blackhole) throws Exception {
        blackhole.consume(BucketSort.sort(arr));
    }

    @Benchmark
    public void radixSort(Blackhole blackhole) throws Exception {
        blackhole.consume(RadixSort.sort(arr));
    }

    public static void main(String[] args) throws RunnerException {

        Options opt = new OptionsBuilder().include(TestSort.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run ();
    }

}
