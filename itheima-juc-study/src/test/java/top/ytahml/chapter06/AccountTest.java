package top.ytahml.chapter06;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * @author 花木凋零成兰
 * @title AccountTest
 * @date 2025-09-09 21:44
 * @package top.ytahml.chapter06
 * @description
 */
public class AccountTest {

    Account account;
    DecimalAccount decimalAccount;

    @Test
    public void test1() {
        account = new AccountUnsafe(10000);
        Account.demo(account);
    }

    @Test
    public void test2() {
        account = new AccountCas(10000);
        Account.demo(account);
    }

    @Test
    public void test3() {
        decimalAccount = new DecimalAccountCas(new BigDecimal("10000"));
        DecimalAccount.demo(decimalAccount);
    }

    @Test
    public void test4() {
        account = new CustomAccountCas(10000);
        Account.demo(account);
    }



}