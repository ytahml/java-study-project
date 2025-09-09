package top.ytahml.chapter06;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author 花木凋零成兰
 * @title AccountUnsafe
 * @date 2025-09-09 21:43
 * @package top.ytahml.chapter06
 * @description
 */
@AllArgsConstructor
@NoArgsConstructor
public class AccountUnsafe implements Account {

    private Integer balance;

    @Override
    public Integer getBalance() {
        return balance;
    }

    @Override
    public void withdraw(Integer amount) {
        this.balance -= amount;
    }
}
