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

    static final int THREAD_NUMBER = 5;
    static final int LOOP_NUMBER = 20000;

    public static void main(String[] args) {
        ThreadSafeSubClass test = new ThreadSafeSubClass();
        for (int i = 0; i < THREAD_NUMBER; i++) {
            new Thread(() -> test.method1(LOOP_NUMBER), "t" + (i + 1)).start();
        }
    }

}

class ThreadUnsafe {

    // 成员变量线程不安全
    final List<String> list = new ArrayList<>();

    public void method1(int loop) {
        for (int i = 0; i < loop; i++) {
            method2();
            method3();
        }
    }

    private void method2() {
        list.add("1");
    }

    private void method3() {
        list.remove(0);
    }

}

class ThreadSafe {

    public void method1(int loop) {
        // 局部变量引用没有暴露在外面
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < loop; i++) {
            method2(list);
            method3(list);
        }
    }

    public void method2(List<String> list) {
        list.add("1");
    }

    public void method3(List<String> list) {
        list.remove(0);
    }

}

class ThreadSafeSubClass extends ThreadSafe {
    @Override
    public void method3(List<String> list) {
        // 局部变量引用 暴露给了新线程；会出现线程安全
        new Thread(() -> list.remove(0)).start();
    }
}
