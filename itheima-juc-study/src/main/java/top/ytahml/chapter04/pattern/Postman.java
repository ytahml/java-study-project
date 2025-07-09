package top.ytahml.chapter04.pattern;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 花木凋零成兰
 * @title Postman
 * @date 2025-07-08 22:10
 * @package top.ytahml.chapter04.pattern
 * @description
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class Postman extends Thread {

    private int id;
    private String mail;

    @Override
    public void run() {
        GuardedObject guardObject = GuardedCollection.getGuardObject(id);
        log.debug("开始送信 id: {}, 内容: {}", id, mail);
        guardObject.complete(mail);
    }

}
