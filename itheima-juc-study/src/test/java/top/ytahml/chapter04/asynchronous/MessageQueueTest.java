package top.ytahml.chapter04.asynchronous;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import top.ytahml.utils.ThreadUtils;

/**
 * @author 花木凋零成兰
 * @title MessageQueueTest
 * @date 2025-07-09 20:52
 * @package top.ytahml.chapter04.asynchronous
 * @description
 */
@Slf4j
public class MessageQueueTest {

    @Test
    public void test1() {

        MessageQueue<String> queue = new MessageQueue<>(2);

        for (int i = 0; i < 3; i++) {
            int finalI = i;
            new Thread(() -> {
                Message<String> message = new Message<>(finalI, "值+1");
                queue.put(message);
            }, "生产者" + i).start();
        }

        ThreadUtils.sleep(2 * 1000);

        new Thread(() -> {
            while (true) {
                ThreadUtils.sleep(1000);
                Message<?> message = queue.take();
            }
        }, "消费者").start();


        ThreadUtils.sleep(10 * 1000);
    }



}