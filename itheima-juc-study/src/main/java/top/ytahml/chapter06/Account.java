package top.ytahml.chapter06;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 花木凋零成兰
 * @title Account
 * @date 2025-09-09 21:37
 * @package top.ytahml.chapter06
 * @description 需求: 保证 account.withdraw 取款方法的线程安全
 */
public interface Account {

    // 获取余额
    Integer getBalance();

    // 取款
    void withdraw(Integer amount);

    /**
     * 方法内会启动 1000 个线程，每个线程做 -10 员操作；
     * 如果初始余额为 1000 那么正确的结果应当是 0
     * @param account 账户示例
     */
    static void demo(Account account) {
        List<Thread> ts = new ArrayList<>();
        long start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            ts.add(new Thread(() -> {
                account.withdraw(10);
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
