package top.ytahml.chapter06.juc;

import lombok.extern.slf4j.Slf4j;
import top.ytahml.utils.ThreadUtils;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 使用带时间戳（版本号）的原子工具类解决 ABA 问题
 * @author 花木凋零成兰
 * @since 2025/9/29 10/22
 */
@Slf4j
public class StringStampedReferenceAtomicTest {

    // 初始值、初始版本号
    static AtomicStampedReference<String> ref = new AtomicStampedReference<>("A", 0);

    public static void main(String[] args) {
        log.info("main start ...");
        // 获取修改前的值、和修改前的版本号
        String prev = ref.getReference();
        int stamp = ref.getStamp();
        log.debug("main prev: {}, stamp: {}", prev, stamp);
        other();
        // 修改后使版本号+1
        ThreadUtils.sleep(1000);
        log.info("change A -> C {}", ref.compareAndSet(prev, "C", stamp, stamp+1));
    }

    private static void other() {
        new Thread(() -> {
            String prev = ref.getReference();
            int stamp = ref.getStamp();
            log.debug("t1 prev: {}, stamp: {}", prev, stamp);
            log.info("change A -> B {}", ref.compareAndSet(prev, "B", stamp, stamp+1));
        }, "t1").start();
        new Thread(() -> {
            // 休眠一段时间，避免修改失败
            ThreadUtils.sleep(200);
            String prev = ref.getReference();
            int stamp = ref.getStamp();
            log.debug("t2 prev: {}, stamp: {}", prev, stamp);
            log.info("change B -> A {}", ref.compareAndSet(prev, "A", stamp, stamp+1));
        }, "t2").start();
    }

}
