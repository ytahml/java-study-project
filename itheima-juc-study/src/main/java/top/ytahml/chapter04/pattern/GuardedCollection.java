package top.ytahml.chapter04.pattern;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 花木凋零成兰
 * @title GuardedCollection
 * @date 2025-07-08 21:56
 * @package top.ytahml.chapter04
 * @description 增强模式-保护性暂停；
 */
public class GuardedCollection {

    private static final Map<Integer, GuardedObject<?>> COLLECT = new ConcurrentHashMap<>();

    private static int id = 1;

    // 等于类对象加锁
    private static synchronized int generatedId() {
        return id ++;
    }

    public static <T> GuardedObject<T> createGuardedObject() {
        GuardedObject<T> guardedObject = new GuardedObject<>(generatedId());
        COLLECT.put(guardedObject.getId(), guardedObject);
        return guardedObject;
    }

    public static Set<Integer> getIds() {
        return COLLECT.keySet();
    }

    public static GuardedObject getGuardObject(int id) {
        return COLLECT.remove(id);
    }

}
