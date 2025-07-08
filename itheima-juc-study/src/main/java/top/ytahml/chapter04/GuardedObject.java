package top.ytahml.chapter04;

import lombok.SneakyThrows;

/**
 * @author 花木凋零成兰
 * @title GuardedObject
 * @date 2025-07-08 20:26
 * @package top.ytahml.chapter04
 * @description
 */
public class GuardedObject<T> {

    // 结果
    private T response;

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

    // 赋值结果
    public void complete(T response) {
        synchronized (this) {
            // 结果赋值，并唤醒所有等待线程
            this.response = response;
            this.notifyAll();
        }
    }

}
