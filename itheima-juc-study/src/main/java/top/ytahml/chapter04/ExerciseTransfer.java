package top.ytahml.chapter04;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * @author 花木凋零成兰
 * @title ExerciseTransfer
 * @date 2025-06-23 21:22
 * @package top.ytahml.chapter04
 * @description 共享变量的读写
 */
@Slf4j
public class ExerciseTransfer {

    private static final Random RANDOM = new Random();

    @SneakyThrows
    public static void main(String[] args) {

        Account a = new Account(1000);
        Account b = new Account(1000);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                a.transfer(b, RANDOM.nextInt(100) + 1);
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                b.transfer(a, RANDOM.nextInt(100) + 1);
            }
        }, "t2");

        t1.start();t2.start();
        t1.join();t2.join();

        log.debug("total: {}", a.getMoney() + b.getMoney());

    }

}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Account {

    private int money;

    // 转账
    public void transfer(Account target, int amount) {
        synchronized (Account.class) {
            // 当a向b转账时，若锁this对象，即锁的是a实例，则保证a向b转正只有一个线程访问修改this.money，即只有一个线程访问a.money
            // 但是无法保证target.money只有一个线程访问修改，即无法阻止b向a转账时，对b.money的修改
            if (this.money >= amount) {
                this.setMoney(this.getMoney() - amount);
                target.setMoney(target.getMoney() + amount);
            }
        }
    }

}
