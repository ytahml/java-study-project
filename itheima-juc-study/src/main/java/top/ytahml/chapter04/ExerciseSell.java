package top.ytahml.chapter04;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 * @author 花木凋零成兰
 * @title ExerciseSell
 * @date 2025-06-23 20:27
 * @package top.ytahml.chapter04
 * @description 卖票练习
 */
@Slf4j
public class ExerciseSell {

    private static Random random = new Random();

    public static void main(String[] args) {

        TicketWindow ticketWindow = new TicketWindow(60000);
        List<Thread> threads = new ArrayList<>();
        // 存储卖出多少张票
        List<Integer> sellCount = new Vector<>();
        for (int i = 0; i < 4000; i++) {
            Thread t = new Thread(() -> {
                // 买票操作
                // 分析竟态条件:
                int count = ticketWindow.sell(random.nextInt(5) + 1);
                sellCount.add(count);
                try {
                    // 增加线程突出
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            threads.add(t);
            t.start();
        }

        try {
            for (Thread t : threads) {
                t.join();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 卖出去的票求和
        log.debug("sold count: {}", sellCount.stream().mapToInt(Integer::intValue).sum());
        log.debug("remainder count:{}", ticketWindow.getCount());

    }

}

@Getter
@NoArgsConstructor
@AllArgsConstructor
class TicketWindow {

    private int count;

    public int sell(int amount) {
        if (this.count >= amount) {
            this.count -= amount;
            // 返回卖出去票的数量统计
            return amount;
        } else {
            return 0;
        }
    }

}
