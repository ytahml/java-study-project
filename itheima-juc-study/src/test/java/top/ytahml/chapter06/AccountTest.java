package top.ytahml.chapter06;

import org.junit.Test;

/**
 * @author 花木凋零成兰
 * @title AccountTest
 * @date 2025-09-09 21:44
 * @package top.ytahml.chapter06
 * @description
 */
public class AccountTest {

    Account account;

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

}