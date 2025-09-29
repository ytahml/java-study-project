package top.ytahml.chapter06.juc;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * 原子字段更新器
 *
 * @author 花木凋零成兰
 * @since 2025/9/29 下午2:43
 */
@Slf4j
public class FiledUpdateAtomicTest {

    public static void main(String[] args) {
        Student stu = new Student();
        AtomicReferenceFieldUpdater<Student, String> updater = AtomicReferenceFieldUpdater.newUpdater(Student.class, String.class, "name");
        boolean result = updater.compareAndSet(stu, null, "imulan");
        System.out.println(result);
        System.out.println(stu);
    }

    @Data
    @ToString
    private static class Student {
        protected volatile String name;
    }

}
