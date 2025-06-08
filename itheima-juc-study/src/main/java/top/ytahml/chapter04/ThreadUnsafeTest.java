package top.ytahml.chapter04;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 花木凋零成兰
 * @title ThreadUnsafeTest
 * @date 2025-06-08 20:07
 * @package top.ytahml.chapter04
 * @description 局部变量引用线程不安全的情况
 */
public class ThreadUnsafeTest {

    static final int THREAD_NUMBER = 2;
    static final int LOOP_NUMBER = 200;

    public static void main(String[] args) {
        ThreadUnsafe test = new ThreadUnsafe();
        for (int i = 0; i < THREAD_NUMBER; i++) {
            new Thread(() -> {
                test.method1(LOOP_NUMBER);
            }, "t" + (i + 1)).start();
        }
    }

}

class ThreadUnsafe {

    final List<String> list = new ArrayList<>();

    public void method1(int loop) {
        for (int i = 0; i < loop; i++) {
            method2();
            method3();
        }
    }

    public void method2() {
        list.add("1");
    }

    public void method3() {
        list.remove(0);
    }

}
