package top.ytahml.chapter08.juc.collection;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * 正式测试单词计数
 * @author 花木凋零成兰
 * @since 2025/10/14 21:22
 */
@Slf4j
public class ConcurrentHashMapTest {

    public static void main(String[] args) {
        // 测试读取文件方法是否可行
//        System.out.println(readFromFile(1));

        // 测试多线程进行单词计数
        demo(
                // 尝试换成 ConcurrentHashMap；结果依旧不对
                () -> new ConcurrentHashMap<String, Integer>(),
                (map, words) -> {
                    words.forEach(word -> {
                        // map 是临界区资源；虽然 ConcurrentHashMap 线程安全，但下述操作步骤线程不安全
                        Integer count = map.get(word);
                        int newValue = count == null ? 1 : count + 1;
                        // 此时线程 put 进去的依旧可能是旧值
                        map.put(word, newValue);
                    });
                }
        );
    }

    @SneakyThrows
    private static <V> void demo(Supplier<Map<String, V>> supplier, BiConsumer<Map<String, V>, List<String>> consumer) {
        // 单词，计数
        Map<String, V> counterMap = supplier.get();
        List<Thread> ts = new ArrayList<>();
        for (int i = 0; i < 26 ; i++) {
            int idx = i;
            Thread thread = new Thread(() -> {
                List<String> words = readFromFile(idx);
                consumer.accept(counterMap, words);
            });
            ts.add(thread);
        }

        ts.forEach(Thread::start);
        for (Thread t : ts) {
            t.join();
        }
        System.out.println(counterMap);
    }

    private static List<String> readFromFile(int idx) {
        String filename = "tmp/" + (idx + 1) + ".txt";
        List<String> result = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("读取文件失败: " + filename, e);
        }
        return result;
    }

}
