package top.ytahml.chapter06;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 花木凋零成兰
 * @title AccountCas
 * @date 2025-09-09 21:54
 * @package top.ytahml.chapter06
 * @description
 */
public class AccountCas implements Account {

    private final AtomicInteger balance;

    public AccountCas(Integer balance) {
        this.balance = new AtomicInteger(balance);
    }

    @Override
    public Integer getBalance() {
        return this.balance.get();
    }

    @Override
    public void withdraw(Integer amount) {
        while (true) {
            // 获取线程最新值
            int prev = balance.get();
            // 要修改的余额
            int next = prev - amount;
            // 真正修改
            boolean result = balance.compareAndSet(prev, next);
            if (result) {
                break;
            }
        }
    }
}
