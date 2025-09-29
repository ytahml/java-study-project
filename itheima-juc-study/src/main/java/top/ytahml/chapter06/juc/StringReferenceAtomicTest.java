package top.ytahml.chapter06.juc;

import lombok.extern.slf4j.Slf4j;
import top.ytahml.utils.ThreadUtils;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 原子引用 字符串
 *
 * @author 花木凋零成兰
 * @since 2025/9/29 10/11
 */
@Slf4j
public class StringReferenceAtomicTest {

    static AtomicReference<String> ref = new AtomicReference<>("A");

    public static void main(String[] args) {
        log.info("main start ...");
        // 获取值
        String prev = ref.get();
        // 共享变量被其他线程修改过
        other();
        ThreadUtils.sleep(1000);
        log.info("change A -> C {}", ref.compareAndSet(prev, "C"));
    }

    private static void other() {
        new Thread(() -> {
            String prev = ref.get();
            log.info("change A -> B {}", ref.compareAndSet(prev, "B"));
        }, "t1").start();
        new Thread(() -> {
            String prev = ref.get();
            log.info("change B -> A {}", ref.compareAndSet(prev, "A"));
        }, "t2").start();
    }

}
