package top.ytahml.api;

/**
 * @author 00103943
 * @date 2025-05-23 09:23
 * @package top.ytahml.api
 * @description 测试配置线程优先级接口
 */
public class ThreadPriorityMain {

    private static class PriorityThread implements Runnable {
        @Override
        public void run() {
            Thread thread = Thread.currentThread();
            int priority = thread.getPriority();
            String name = thread.getName();
            System.out.printf("当前由 %s 线程执行, 该线程优先级为: %d%n", name, priority);
        }
    }

    public static void main(String[] args) {
        PriorityThread task = new PriorityThread();
        Thread thread = new Thread(task);
        // 设置线程优先级 1-10
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
        System.out.printf("当前由 %s 线程执行, 该线程优先级为: %d%n", Thread.currentThread().getName(), Thread.currentThread().getPriority());
    }

}
