package top.ytahml.chapter04.pattern;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import top.ytahml.utils.Downloader;
import top.ytahml.utils.ThreadUtils;

import java.util.List;

/**
 * @author 花木凋零成兰
 * @title GuardedObjectTest
 * @date 2025-07-08 20:31
 * @package top.ytahml.chapter04
 * @description 测试：模式-保护性暂停
 */
@Slf4j
public class GuardedObjectTest {

    // 测试：模式-保护性暂停实现
    @SneakyThrows
    @Test
    public void test1() {
        GuardedObject<List<String>> guardedObject = new GuardedObject<>();
        Thread t1 = new Thread(() -> {
            // 等待结果
            log.debug("等待结果...");
            List<String> result = guardedObject.get();
            log.debug("结果大小: [{}]", result.size());
            log.info("输出下载内容: [{}]", result);
        }, "t1");


        Thread t2 = new Thread(() -> {
            log.debug("执行下载");
            List<String> download = Downloader.download();
            guardedObject.complete(download);
        }, "t2");
        t1.start();
        t2.start();

        // 测试线程默认不会等待子线程执行结束，因此得手动等待
        t2.join();
        t1.join();
    }

    // 测试：模式-保护性暂停实现；存在暂停时间
    @SneakyThrows
    @Test
    public void test2() {
        GuardedObject<List<String>> guardedObject = new GuardedObject<>();
        Thread t1 = new Thread(() -> {
            // 等待结果
            log.debug("等待结果...");
            List<String> result = guardedObject.get(2000);
            log.info("输出下载内容: [{}]", result);
        }, "t1");


        Thread t2 = new Thread(() -> {
            log.debug("执行下载");
//            List<String> download = Downloader.download();
            log.debug("下载结束");
            ThreadUtils.sleep(1000);
            guardedObject.complete(null);
        }, "t2");
        t1.start();
        t2.start();

        // 测试线程默认不会等待子线程执行结束，因此得手动等待
        t2.join();
        t1.join();
    }

}