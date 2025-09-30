package top.ytahml.chapter07;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * SimpleDateFormat 对象线程不安全
 *
 * @author 花木凋零成兰
 * @since 2025/9/30 上午10:47
 */
@Slf4j
public class SimpleDateFormatSycTest {

    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    log.debug("{}", sdf.parse("1961-02-12"));
                } catch (ParseException e) {
                    log.error("{}", e.getMessage());
                }
            }, "t" + (i+1)).start();
        }
    }

}
