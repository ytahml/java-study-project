package top.ytahml.chapter06.juc;

import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 获取Unsafe实例
 *
 * @author 花木凋零成兰
 * @since 2025/9/30 上午9:24
 */
@Slf4j
public class UnsafeTest {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe unsafe = (Unsafe) theUnsafe.get(null);
        System.out.println(unsafe);
    }

}
