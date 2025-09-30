package top.ytahml.chapter06.juc;

import lombok.Data;
import lombok.ToString;
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

        // 1、获取属性/域偏移地址
        long id_offset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("id"));
        long name_offset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("name"));

        Teacher teacher = new Teacher();
        // 2、执行 CAS 操作
        unsafe.compareAndSwapInt(teacher, id_offset, 0, 1);
        unsafe.compareAndSwapObject(teacher, name_offset, null, "imulan");

        // 3、验证修改结果
        System.out.println(teacher);

    }

    @Data
    @ToString
    private static class Teacher {
        volatile int id;
        volatile String name;
    }

}
