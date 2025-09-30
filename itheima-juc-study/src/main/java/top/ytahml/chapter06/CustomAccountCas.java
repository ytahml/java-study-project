package top.ytahml.chapter06;

import top.ytahml.chapter06.juc.CustomAtomicClass;

/**
 * 请添加类的描述信息.
 *
 * @author 花木凋零成兰
 * @since 2025/9/30 上午9:45
 */
public class CustomAccountCas implements Account {

    CustomAtomicClass balance;

    public CustomAccountCas(int balance) {
        this.balance = new CustomAtomicClass(balance);
    }

    @Override
    public Integer getBalance() {
        return balance.getValue();
    }

    @Override
    public void withdraw(Integer amount) {
        balance.decrement(amount);
    }
}
