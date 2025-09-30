package top.ytahml.chapter06.juc;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 自定义原子类
 *
 * @author 花木凋零成兰
 * @since 2025/9/30 上午9:35
 */
public class CustomAtomicClass {
    // 定义原子类存储的值
    private volatile int value;
    // 字段偏移量
    private static final long VALUE_OFFSET;

    static final Unsafe UNSAFE;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            UNSAFE = (Unsafe) theUnsafe.get(null);
            VALUE_OFFSET = UNSAFE.objectFieldOffset(CustomAtomicClass.class.getDeclaredField("value"));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    public CustomAtomicClass(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean compareAndSet(int prev, int next) {
        return UNSAFE.compareAndSwapInt(this, VALUE_OFFSET, prev, next);
    }

    public void decrement(int amount) {
        while (true) {
            int prev = getValue();
            int next = prev - amount;
            if (compareAndSet(prev, next)) {
                break;
            }
        }
    }



}
