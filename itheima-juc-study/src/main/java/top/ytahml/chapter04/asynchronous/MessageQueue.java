package top.ytahml.chapter04.asynchronous;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

/**
 * @author 花木凋零成兰
 * @title MessageQueue
 * @date 2025-07-09 20:39
 * @package top.ytahml.chapter04.asynchronous
 * @description Java线程之间通信的消息队列类
 */
@Slf4j
@Getter
public class MessageQueue<T> {

    // 消息集合 - 双向队列
    private final LinkedList<Message<?>> list = new LinkedList<>();
    // 队列容量
    private final int capacity;

    public MessageQueue(int capacity) {
        this.capacity = capacity;
    }

    /**
     * 获取消息
     */
    @SneakyThrows
    public Message<?> take() {
        // 队列为空时，不能消费；等待
        synchronized (list) {
            while (list.isEmpty()) {
                log.debug("队列为空, 消费者线程等待 ...");
                list.wait();
            }
            // 队列头部获取元素并返回；
            Message<?> message = list.removeFirst();
            log.debug("已消费消息: {}", message);
            list.notifyAll();
            return message;
        }
    }

    /**
     * 存入消息
     */
    @SneakyThrows
    public void put(Message<T> message) {
        synchronized (list) {
            // 检查队列是否已满，满了则不可以存入消息
            while (list.size() == capacity) {
                log.debug("队列已满, 生产者线程等待 ...");
                list.wait();
            }
            list.addLast(message);
            log.debug("已生产消息: {}", message);
            // 唤醒等待消息的线程;
            list.notifyAll();
        }
    }

}
