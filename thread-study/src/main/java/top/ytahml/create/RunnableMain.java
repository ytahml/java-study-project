package top.ytahml.create;

/**
 * @author 00103943
 * @date 2025-05-21 17:00
 * @package top.ytahml.create
 * @description 创建线程方式一：实现Runnable接口
 */
public class RunnableMain {

    private static class RunnableThread implements Runnable {
        @Override
        public void run() {
            System.out.println("当前线程: " + Thread.currentThread().getName());
            System.out.println("使用Runnable创建线程 ... ");
        }
    }
    public static void main(String[] args) {
        RunnableThread runnableThread = new RunnableThread();
        Thread thread = new Thread(runnableThread);
        System.out.println("当前为主线程: " + Thread.currentThread().getName());
        thread.start();
    }

}
