package top.ytahml.chapter04.locks;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 花木凋零成兰
 * @title Philosopher
 * @date 2025-07-13 15:17
 * @package top.ytahml.chapter04.locks
 * @description 哲学家
 */
@Slf4j
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Philosopher extends Thread {

    Chopstick left;

    Chopstick right;

    public Philosopher(String name, Chopstick left, Chopstick right) {
        super(name);
        this.left = left;
        this.right = right;
    }

    @SneakyThrows
    private void eat() {
        log.debug("eating ...");
        sleep(1000);
    }

    @Override
    public void run() {
        while (true) {
            // 获得左手筷子
            if (left.tryLock()) {
                // 右手筷子
                try {
                    if (right.tryLock()) {
                        try {
                            eat();
                        } finally {
                            right.unlock();
                        }
                    }
                } finally {
                    left.unlock();
                }
                // 放下右手
            }
            // 放下左手
        }
    }
}

