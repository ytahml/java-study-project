package top.ytahml.chapter08.pattern.syn;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 饥饿现象测试
 *
 * @author 花木凋零成兰
 * @since 2025/10/1 下午4:54
 */
@Slf4j
public class StarvationTest {

    static final List<String> MENU;
    static final Random RANDOM;

    static {
        MENU = Arrays.asList("地三鲜", "宫保鸡丁", "辣子鸡丁", "烤鸡翅");
        RANDOM = new Random();
    }

    static String cooking() {
        return MENU.get(RANDOM.nextInt(MENU.size()));
    }

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        // 一个客人，点餐
        pool.execute(() -> {
            log.debug("开始处理点餐 ...");
            Future<String> result = pool.submit(() -> {
                log.debug("做菜任务 ...");
                return cooking();
            });
            try {
                log.debug("上菜: {}", result.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

        // 此时来了另一个客人，点餐
        pool.execute(() -> {
            log.debug("开始处理点餐 ...");
            Future<String> result = pool.submit(() -> {
                log.debug("做菜任务 ...");
                return cooking();
            });
            try {
                log.debug("上菜: {}", result.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

    }

}
