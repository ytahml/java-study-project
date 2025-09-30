package top.ytahml.chapter08.pool;

/**
 * 当阻塞队列已满时，线程池拒绝策略
 *
 * @author 花木凋零成兰
 * @since 2025/9/30 下午11:14
 */
@FunctionalInterface
public interface RejectPolicy<T> {

    void reject(BlockingQueue<T> queue, T task);

}
