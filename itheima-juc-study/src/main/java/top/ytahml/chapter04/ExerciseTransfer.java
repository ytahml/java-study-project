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
 * @description
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
        if (this.money >= amount) {
            this.setMoney(this.getMoney() - amount);
            target.setMoney(target.getMoney() + amount);
        }
    }

}
