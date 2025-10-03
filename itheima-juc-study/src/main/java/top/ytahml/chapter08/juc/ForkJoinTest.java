package top.ytahml.chapter08.juc;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Fort/JOIN 线程池
 * 求 1 ~ n 整数和
 * @author 花木凋零成兰
 * @since 2025/10/3 下午2:42
 */
public class ForkJoinTest {

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool(4);
        System.out.println(pool.invoke(new MyTask(5)));

    }

    @Setter
    @Getter
    @Slf4j
    private static class MyTask extends RecursiveTask<Integer> {

        private int n;

        public MyTask(int n) {
            this.n = n;
        }

        @SneakyThrows
        @Override
        protected Integer compute() {
            log.info("{}", n);
            // 终止条件
            if (n == 1) {
                return 1;
            }
            // 让一个线程继续执行子任务
            ForkJoinTask<Integer> fork = new MyTask(n - 1).fork();
            return n + fork.join();
        }
    }

}
