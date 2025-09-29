package top.ytahml.chapter06.juc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import top.ytahml.utils.ThreadUtils;

import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * 不关系原子引用被修改了几次，只关系是否被修改过，可以使用
 * {@link java.util.concurrent.atomic.AtomicMarkableReference}
 * @author 花木凋零成兰
 * @since 2025/9/29 下午1:37
 */
@Slf4j
public class MarkableReferenceAtomicTest {

    public static void main(String[] args) {
        GarbageBag bag = new GarbageBag("装满了垃圾");
        // 参数2 mark 可以看作一个标记；标识垃圾袋装满了
        AtomicMarkableReference<GarbageBag> ref = new AtomicMarkableReference<>(bag, true);
        log.info("main start ...");
        GarbageBag prev = ref.getReference();
        log.debug("main prev: {}", prev);

        // 保洁阿姨线程
        new Thread(() -> {
            log.info("start ...");
            bag.setDesc("空垃圾袋");
            ref.compareAndSet(bag, bag, true, false);
            log.debug("保洁阿姨更换结果: {}", bag);
        }, "保洁阿姨").start();

        ThreadUtils.sleep(1000);
        log.debug("想换一只新的垃圾袋?");
        boolean result = ref.compareAndSet(prev, new GarbageBag("空垃圾袋"), true, false);
        log.debug("换了吗? {}", result);
        log.debug("更换结果: {}", ref.getReference());
    }

    @Data
    @ToString
    @AllArgsConstructor
    private static class GarbageBag {
        String desc;
    }

}
