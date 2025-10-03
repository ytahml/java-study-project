package top.ytahml.chapter08;

import lombok.extern.slf4j.Slf4j;
import top.ytahml.utils.ThreadUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Timer Java自带定时器：所有任务串行执行，前面任务的执行异常会影响到后面线程
 *
 * @author 花木凋零成兰
 * @since 2025/10/3 下午1:18
 */
@Slf4j
public class TimerTest {

    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                log.debug("run task 1 ...");
                ThreadUtils.sleep(2000);
            }
        };

        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                log.debug("run task 2 ...");
            }
        };

        log.debug("Timer start ...");
        // 1s 之后执行任务1和任务2
        timer.schedule(task1, 1000);
        timer.schedule(task2, 1000);
    }

}
