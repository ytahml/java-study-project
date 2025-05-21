package top.ytahml.create;

/**
 * @author 00103943
 * @date 2025-05-21 17:09
 * @package top.ytahml.create
 * @description 创建线程方式二：继承Thread类
 */
public class ThreadMain {

    private static class ThreadChild extends Thread {
        @Override
        public void run() {
            System.out.println("当前线程: " + Thread.currentThread().getName());
            System.out.println("继承Thread实现多线程 ... ");
        }
    }

    public static void main(String[] args) {
        ThreadChild child = new ThreadChild();
        child.start();
        System.out.println("主线程: " + Thread.currentThread().getName());
    }

}
