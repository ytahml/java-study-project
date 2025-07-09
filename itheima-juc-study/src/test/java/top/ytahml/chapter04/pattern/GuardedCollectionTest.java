package top.ytahml.chapter04.pattern;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import top.ytahml.utils.ThreadUtils;

import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author 花木凋零成兰
 * @title GuardedCollectionTest
 * @date 2025-07-08 22:10
 * @package top.ytahml.chapter04.pattern
 * @description
 */
@Slf4j
public class GuardedCollectionTest {

    @Test
    public void test1() {
        for (int i = 0; i < 3; i++) {
            new People().start();
        }

        ThreadUtils.sleep(3000);
        Set<Integer> ids = GuardedCollection.getIds();
        for (Integer id : ids) {
            new Postman(id, "内容: 送信 " + id).start();
        }

        ThreadUtils.sleep(10*1000);
    }

}