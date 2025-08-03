package top.ytahml.chapter03;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 花木凋零成兰
 * @title TwoPhaseTerminationTest
 * @date 2025-06-08 09:19
 * @package top.ytahml.chapter03
 * @description 测试两阶段终止设计模式
 */
@Slf4j(topic = "c.Test")
public class TwoPhaseTerminationTest {

    @SneakyThrows
    public static void main(String[] args) {

        TwoPhaseTermination tpt = new TwoPhaseTermination();
        tpt.start();
        tpt.start();
        tpt.start();

        Thread.sleep(5000);

        tpt.stop();

    }

}

@Slf4j(topic = "c.TwoPhaseTermination")
class TwoPhaseTermination {

    private Thread monitor;

    private volatile boolean stop;

    // 是否已经执行过
    private boolean starting;

    // 启动监控线程
    public void start() {

        synchronized (this) {
            if (starting) {
                return;
            }
            starting = true;
        }

        monitor = new Thread(() -> {
            while (true) {
                Thread current = Thread.currentThread();
//                if (current.isInterrupted()) {
//                    // 线程被打断
//                    log.debug("料理后事 ...");
//                    break;
//                }

                if (stop) {
                    // 线程被打断
                    log.debug("料理后事 ...");
                    break;
                }

                try {
                    Thread.sleep(1000);
                    log.debug("执行监控记录 ...");
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
//                    current.interrupt();
                }

            }
        }, "monitor");
        monitor.start();
    }

    // 关闭监控线程
    public void stop() {
//        monitor.interrupt();
        stop = true;
        monitor.interrupt();
    }

}
