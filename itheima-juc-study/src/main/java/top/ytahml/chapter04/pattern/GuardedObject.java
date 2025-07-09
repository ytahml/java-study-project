package top.ytahml.chapter04.pattern;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

/**
 * @author 花木凋零成兰
 * @title GuardedObject
 * @date 2025-07-08 20:26
 * @package top.ytahml.chapter04
 * @description
 */
@NoArgsConstructor
public class GuardedObject<T> {

    @Getter
    private int id;

    // 结果
    private T response;


    public GuardedObject(int id) {
        this.id = id;
    }

    // 获取结果
    @SneakyThrows
    public T get() {
        synchronized (this) {
            while (response == null) {
                this.wait();
            }
            return response;
        }
    }

    // 获取结果；增加超时时间
    @SneakyThrows
    public T get(long timeout) {
        synchronized (this) {
            // 记录一个开始时间
            long begin = System.currentTimeMillis();
            // 经历时间
            long passedTime = 0;
            while (response == null) {
                long waitTime = timeout - passedTime;
                if (waitTime <= 0) {
                    break;
                }
                this.wait(waitTime);
                // 经理时间
                passedTime = System.currentTimeMillis() - begin;
            }
            return response;
        }
    }

    // 赋值结果
    public void complete(T response) {
        synchronized (this) {
            // 结果赋值，并唤醒所有等待线程
            this.response = response;
            this.notifyAll();
        }
    }

}
