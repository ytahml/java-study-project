package top.ytahml.chapter04;

import lombok.extern.slf4j.Slf4j;
import top.ytahml.utils.ThreadUtils;

/**
 * @author 花木凋零成兰
 * @title WaitAndNotifyTests
 * @date 2025-07-07 20:12
 * @package top.ytahml.chapter04
 * @description wait 和 notify 的正确使用姿势
 */
@Slf4j
public class WaitAndNotifyTests {

    // 房间
    static final Object ROOM = new Object();
    // 是否有🚬
    static boolean hasCigarette = false;
    static boolean hasTakeout = false;

    public static void main(String[] args) {

        new Thread(() -> {
            synchronized (ROOM) {
                log.debug("外卖送到没? [{}]", hasTakeout);
                while (!hasTakeout) {
                    log.debug("没外卖, 先歇会!");
//                    ThreadUtils.sleep(2000);
                    try {
                        ROOM.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                log.debug("外卖送到没? [{}]", hasTakeout);
                if (hasTakeout) {
                    log.debug("可以开始干活了");
                } else {
                    log.debug("没干成活!");
                }
            }
        }, "小兰").start();

        new Thread(() -> {
            synchronized (ROOM) {
                log.debug("有烟没? [{}]", hasCigarette);
                while (!hasCigarette) {
                    log.debug("没烟, 先歇会!");
//                    ThreadUtils.sleep(2000);
                    try {
                        ROOM.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                log.debug("有烟没? [{}]", hasCigarette);
                if (hasCigarette) {
                    log.debug("可以开始干活了");
                }
            }
        }, "小南").start();

        for (int i = 0; i < 5; ++ i) {
            new Thread(() -> {
                synchronized (ROOM) {
                    log.debug("可以开始干活了");
                }
            }, "其他人").start();
        }

        ThreadUtils.sleep(1000);
        new Thread(() -> {
            synchronized (ROOM) {
                hasCigarette = true;
                log.debug("烟到了噢!");
                ROOM.notifyAll();
            }
        }, "送烟的").start();

    }

}
