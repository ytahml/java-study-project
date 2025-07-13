package top.ytahml.chapter04.locks;

/**
 * @author 花木凋零成兰
 * @title PhilosopherTest
 * @date 2025-07-13 15:21
 * @package top.ytahml.chapter04.locks
 * @description
 */
public class PhilosopherTest {

    public static void main(String[] args) {
        // 就餐
        Chopstick c1 = new Chopstick("c1");
        Chopstick c2 = new Chopstick("c2");
        Chopstick c3 = new Chopstick("c3");
        Chopstick c4 = new Chopstick("c4");
        Chopstick c5 = new Chopstick("c5");

        new Philosopher("苏格拉底", c1, c2).start();
        new Philosopher("柏拉图", c2, c3).start();
        new Philosopher("亚里士多德", c3, c4).start();
        new Philosopher("赫拉克利特", c4, c5).start();
        new Philosopher("阿基米德", c5, c1).start();

    }

}