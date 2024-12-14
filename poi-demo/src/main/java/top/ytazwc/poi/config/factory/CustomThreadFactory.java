package top.ytazwc.poi.config.factory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 花木凋零成兰
 * @title CustomThreadFactory
 * @date 2024-12-14 11:45
 * @package top.ytazwc.poi.config.factory
 * @description 自定义线程工厂 - 定义线程名
 */
public class CustomThreadFactory implements ThreadFactory {

    // 生成唯一的线程编号
    private final AtomicInteger threadNumber;
    // 线程名前缀
    private final String namePrefix;

    public CustomThreadFactory(String namePrefix) {
        this.namePrefix = namePrefix;
        threadNumber = new AtomicInteger(1);
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r,namePrefix + "-thread-" + threadNumber.getAndIncrement());
    }
}
