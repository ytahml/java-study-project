package top.ytahml.chapter03;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * @author 花木凋零成兰
 * @title ParkTest
 * @date 2025-06-08 10:42
 * @package top.ytahml.chapter03
 * @description 测试打断park
 */
@Slf4j
public class ParkTest {

    @SneakyThrows
    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            log.debug("park ...");
            LockSupport.park();
            log.debug("un park ...");
            log.debug("打断状态: {}", Thread.currentThread().isInterrupted());

            // 打断标记为真的情况下，再次 park 会失效
            // 可以在获取打断状态时，调用interrupted方法，清除打断标记
            LockSupport.park();
            log.debug("un park ...");
        }, "t1");

        t1.start();

        Thread.sleep(2000);
        t1.interrupt();


    }

}
