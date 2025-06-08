package top.ytahml.chapter03;

import lombok.extern.slf4j.Slf4j;
import top.ytahml.utils.ThreadUtils;

/**
 * @author 花木凋零成兰
 * @title Exercise
 * @date 2025-06-08 12:35
 * @package top.ytahml.chapter03
 * @description
 */
@Slf4j
public class Exercise1 {

    public static void main(String[] args) throws InterruptedException {

        // a
        Thread t1 = new Thread(() -> {
            log.debug("洗水壶 ...");
            ThreadUtils.sleep(1000);
            log.debug("烧开水 ...");
            ThreadUtils.sleep(15000);
        }, "t1");

        // b
        Thread t2 = new Thread(() -> {
            log.debug("洗茶壶 ...");
            ThreadUtils.sleep(1000);
            log.debug("洗茶杯 ...");
            ThreadUtils.sleep(2000);
            log.debug("拿茶叶 ...");
            ThreadUtils.sleep(1000);
            // b 执行最后的泡茶
            try {
                t1.join();
                log.debug("泡茶 ... ");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "t2");

        t1.start();
        t2.start();

    }

}
