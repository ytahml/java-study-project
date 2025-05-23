package top.ytahml.create;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author 00103943
 * @date 2025-05-22 10:44
 * @package top.ytahml.create
 * @description 实现 Callable 接口
 */
public class CallableMain {

    private static class CallableThread implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            System.out.println("当前线程: " + Thread.currentThread().getName());
            System.out.println("使用Callable创建线程 ... ");
            Random random = new Random();
            return random.nextInt();
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CallableThread callableThread = new CallableThread();
        FutureTask<Integer> futureTask = new FutureTask<>(callableThread);
        Thread thread = new Thread(futureTask);
        thread.start();
        Integer result = futureTask.get();
        System.out.println(result);
    }
}
