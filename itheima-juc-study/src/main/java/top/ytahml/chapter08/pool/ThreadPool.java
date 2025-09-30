package top.ytahml.chapter08.pool;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 自定义线程池
 *
 * @author 花木凋零成兰
 * @since 2025/9/30 下午9:16
 */
@Slf4j
public class ThreadPool {

    // 任务队列
    @Getter
    private final BlockingQueue<Runnable> taskQueue;
    // 线程集合
    private final HashSet<Worker> workers = new HashSet<>();

    // 核心线程数
    @Getter
    @Setter
    private int coreSize;
    // 任务超时时间
    @Getter
    @Setter
    private long timeout;
    // 超时时间单位
    @Getter
    @Setter
    private TimeUnit timeUnit;

    public ThreadPool(int coreSize, long timeout, TimeUnit timeUnit, int queueCapacity) {
        this.coreSize = coreSize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.taskQueue = new BlockingQueue<>(queueCapacity);
    }

    // 任务执行方法
    public void execute(Runnable task) {
        // 任务数没有超过 coreSize 时，直接交给 worker 对象执行
        // 如果任务数超过 coreSize 时，加入任务队列暂存
        synchronized (workers) {
            if (workers.size() < coreSize) {
                Worker worker = new Worker(task);
                log.warn("add worker: {}, execute task: {}", worker, task);
                workers.add(worker);
                worker.start();
            } else {
                log.info("task join queue: {}", task);
                taskQueue.put(task);
            }
        }
    }

    // 线程信息再包装
    private class Worker extends Thread {
        private Runnable task;

        public Worker(Runnable task) {
            super(task);
            this.task = task;
        }

        @Override
        public void run() {
            // 执行任务
            // 1、当 task 不为空时，执行任务
            // 2、当 task 执行完后，再接着从任务队列获取任务
            while (Objects.nonNull(task) || Objects.nonNull((task = taskQueue.poll(timeout, timeUnit)))) {
                try {
                    log.debug("worker running task: {}", task);
                    task.run();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    task = null;
                }
            }
            synchronized (workers) {
                log.warn("worker removed: {}", this);
                workers.remove(this);
            }
        }
    }
}
