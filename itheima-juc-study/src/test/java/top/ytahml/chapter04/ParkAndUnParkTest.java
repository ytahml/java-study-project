package top.ytahml.chapter04;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import top.ytahml.utils.ThreadUtils;

import java.util.concurrent.locks.LockSupport;

/**
 * @author 花木凋零成兰
 * @title ParkAndUnParkTest
 * @date 2025-07-10 20:20
 * @package top.ytahml.chapter04
 * @description
 */
@Slf4j
public class ParkAndUnParkTest {

    @Test
    public void test1() {
        Thread t1 = new Thread(() -> {
            log.debug("start ...");
            ThreadUtils.sleep(3000);
            log.debug("park ...");
            LockSupport.park();
            log.debug("resume ...");
        }, "t1");

        t1.start();
        ThreadUtils.sleep(2000);
        log.debug("unpark ...");
        LockSupport.unpark(t1);

        ThreadUtils.sleep(3000);
    }

}