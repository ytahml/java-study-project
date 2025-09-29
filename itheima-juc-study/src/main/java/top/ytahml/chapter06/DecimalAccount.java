package top.ytahml.chapter06;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * BigDecimal类型的账户余额
 * @author 花木凋零成兰
 * @since 2025/9/29 09/47
 */
public interface DecimalAccount {

    // 获取余额
    BigDecimal getBalance();

    // 取款
    void withdraw(BigDecimal amount);

    /**
     * 启动1000个线程，每个先成做 -10 元操作
     * 如果初始余额为 10000 则正确结果应该是0
     */
    static void demo(DecimalAccount account) {
        List<Thread> ts = new ArrayList<>();
        long start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            ts.add(new Thread(() -> {
                account.withdraw(BigDecimal.TEN);
            }));
        }
        ts.forEach(Thread::start);
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.nanoTime();
        System.out.println(account.getBalance() + " cost: " + (end-start)/1000_000 + " ms");
    }

}
