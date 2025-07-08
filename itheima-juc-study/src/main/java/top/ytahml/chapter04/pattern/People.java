package top.ytahml.chapter04.pattern;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 花木凋零成兰
 * @title People
 * @date 2025-07-08 22:11
 * @package top.ytahml.chapter04.pattern
 * @description
 */
@Slf4j
public class People extends Thread {

    @Override
    public void run() {
        // 收信
        GuardedObject<String> guardedObject = GuardedCollection.createGuardedObject();
        int id = guardedObject.getId();
        log.debug("收信id: [{}]", id);
        Object o = guardedObject.get(5000);
        log.debug("收到信id: {}, 内容: {}", id, o);
    }

}
