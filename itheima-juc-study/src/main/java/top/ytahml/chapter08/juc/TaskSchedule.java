package top.ytahml.chapter08.juc;

import lombok.extern.slf4j.Slf4j;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务；每周四 18 点定时执行任务
 *
 * @author 花木凋零成兰
 * @since 2025/10/3 下午1:47
 */
@Slf4j
public class TaskSchedule {

    public static void main(String[] args) {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        // 当前时间和周四的时间查
        LocalDateTime now = LocalDateTime.now();
        // 本周 周四时间
        LocalDateTime time = now.withHour(18).withMinute(0).withSecond(0).withNano(0).with(DayOfWeek. THURSDAY);
        System.out.println(time);
        if (now.isAfter(time)) {
            // 若当前时间在本周周四之后，则移到下周周四
            time = time.plusWeeks(1);
        }
        System.out.println(time);
        // 当前时间距周四的时间间隔
        long initialDelay = Duration.between(now, time).toMillis();
        // 一周的时间间隔
        long period = 1000 * 60 * 60 * 24 * 7;
        pool.scheduleAtFixedRate(() -> {
            log.info("running ... ");
        }, initialDelay, period, TimeUnit.MICROSECONDS);
    }

}
