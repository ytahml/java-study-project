package top.ytahml.chapter04.locks;

import org.junit.Test;

import static top.ytahml.utils.ThreadUtils.sleep;

/**
 * @author 花木凋零成兰
 * @title MultiLockTest
 * @date 2025-07-10 21:42
 * @package top.ytahml.chapter04
 * @description 多把锁
 */
public class MultiLockTest {

    @Test
    public void test1() {
        BigRoom bigRoom = new BigRoom();
        new Thread(bigRoom::study,"小南").start();
        new Thread(bigRoom::sleep,"小女").start();
        sleep(5000);
    }

}