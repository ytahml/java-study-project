package top.ytahml.chapter04;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 花木凋零成兰
 * @title ExampleClass
 * @date 2025-06-08 16:43
 * @package top.ytahml.chapter04
 * @description 并发示例，按照面向对象的方式改造
 */
@Slf4j
public class ExampleClass {

    public static void main(String[] args) throws InterruptedException {

        Room room = new Room();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                room.increment();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                room.decrement();
            }
        }, "t2");
        t1.start();
        t2.start();

        t1.join();
        t2.join();
        log.debug("counter = {}", room.getCounter());

    }

}

class Room {

    private int counter = 0;

    public void increment() {
        synchronized (this) {
            counter ++;
        }
    }

    public void decrement() {
        synchronized (this) {
            counter --;
        }
    }

    public int getCounter() {
        synchronized (this) {
            return counter;
        }
    }

}
