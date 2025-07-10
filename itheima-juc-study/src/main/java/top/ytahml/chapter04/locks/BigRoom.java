package top.ytahml.chapter04.locks;

import lombok.extern.slf4j.Slf4j;
import top.ytahml.utils.ThreadUtils;

/**
 * @author 花木凋零成兰
 * @title BigRoom
 * @date 2025-07-10 21:43
 * @package top.ytahml.chapter04.locks
 * @description
 */
@Slf4j
public class BigRoom {

    public void sleep() {
        synchronized (this) {
            log.debug("睡觉2小时 ...");
            ThreadUtils.sleep(2000);
        }
    }

    public void study() {
        synchronized (this) {
            log.debug("学习1小时 ...");
            ThreadUtils.sleep(1000);
        }
    }

}
