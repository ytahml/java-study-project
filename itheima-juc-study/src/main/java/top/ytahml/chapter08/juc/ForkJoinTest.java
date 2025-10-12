package top.ytahml.chapter08.juc;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.ToString;
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
//        System.out.println(pool.invoke(new MyTask(5)));
        System.out.println(pool.invoke(new AddTask(1, 5)));

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

    @ToString
    @Slf4j
    private static class AddTask extends RecursiveTask<Integer> {

        int begin;
        int end;

        public AddTask(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if (begin == end) {
                log.debug("join: {}", begin);
                return begin;
            }
            if (end - begin == 1) {
                log.debug("join: {} + {} = {}", begin, end, begin + end);
                return begin + end;
            }


            // 根据中间值，分别求和
            int mid = begin + (end - begin) / 2;
            AddTask t1 = new AddTask(begin, mid);
            t1.fork();
            AddTask t2 = new AddTask(mid + 1, end);
            t2.fork();
            log.debug("fork: {} + {} = ?", t1, t2);
            int result = t1.join() + t2.join();
            log.debug("join: {} + {} = {}", t1, t2, result);
            return result;
        }
    }

}
