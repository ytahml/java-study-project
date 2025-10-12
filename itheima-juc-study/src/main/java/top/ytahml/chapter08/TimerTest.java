package top.ytahml.chapter08;

import lombok.extern.slf4j.Slf4j;
import top.ytahml.utils.ThreadUtils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Timer Java自带定时器：所有任务串行执行，前面任务的执行异常会影响到后面任务
 *
 * @author 花木凋零成兰
 * @since 2025/10/3 下午1:18
 */
@Slf4j
public class TimerTest {

    public static void main(String[] args) {
//        Timer timer = new Timer();
//        TimerTask task1 = new TimerTask() {
//            @Override
//            public void run() {
//                log.debug("run task 1 ...");
//                ThreadUtils.sleep(2000);
//            }
//        };
//
//        TimerTask task2 = new TimerTask() {
//            @Override
//            public void run() {
//                log.debug("run task 2 ...");
//            }
//        };
//
//        log.debug("Timer start ...");
//        // 1s 之后执行任务1和任务2
//        timer.schedule(task1, 1000);
//        timer.schedule(task2, 1000);

        // 不同线程执行任务不会相互影响；执行出现异常，后续任务还能继续执行
        ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(1);
//        scheduledPool.schedule(() -> {
//            log.debug("schedule task 1 ...");
//            ThreadUtils.sleep(2000);
//            int a = 1/0;
//        }, 1, TimeUnit.SECONDS);
//
//        scheduledPool.schedule(() -> {
//            log.debug("schedule task 2 ...");
//        }, 1, TimeUnit.SECONDS);

        // 固定时间间隔执行一个任务
        log.debug("task start ...");
        // 初始延迟时间 1s，时间间隔 1s
//        scheduledPool.scheduleAtFixedRate(() -> {
//            log.debug("task running ...");
//            // 任务本身的执行时间，会影响到任务执行的时间间隔
//            ThreadUtils.sleep(2000);
//        }, 1, 1, TimeUnit.SECONDS);

        // 另一个定时任务执行 API
        // 任务执行间隔时间，是从上一个任务执行完成后开始计算
        scheduledPool.scheduleWithFixedDelay(() -> {
            log.debug("task running ...");
            // 任务本身的执行时间+设置的间隔时间
            ThreadUtils.sleep(2000);
        }, 1, 1, TimeUnit.SECONDS);
    }

}
