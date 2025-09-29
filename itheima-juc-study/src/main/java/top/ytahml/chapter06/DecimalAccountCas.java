package top.ytahml.chapter06;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 引用类型账号信息
 *
 * @author 花木凋零成兰
 * @since 2025/9/29 09/46
 */
public class DecimalAccountCas implements DecimalAccount {

    private final AtomicReference<BigDecimal> balance;

    public DecimalAccountCas(BigDecimal balance) {
        this.balance = new AtomicReference<>(balance);
    }

    @Override
    public BigDecimal getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(BigDecimal amount) {
        while (true) {
            BigDecimal prev = balance.get();
            BigDecimal next = prev.subtract(amount);
            boolean result = balance.compareAndSet(prev, next);
            if (result) {
                break;
            }
        }
    }
}
