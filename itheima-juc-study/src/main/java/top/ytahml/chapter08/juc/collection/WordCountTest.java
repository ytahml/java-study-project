package top.ytahml.chapter08.juc.collection;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 测试例子：单词计数，生成 tem 文件夹及内容
 * @author 花木凋零成兰
 * @since 2025/10/14 20:52
 */
@Slf4j
public class WordCountTest {

    final static String ALPHA = "abcdefghijklmnopqrstuvwxyz";

    public static void main(String[] args) throws IOException {
        int len = ALPHA.length();
        int count = 200;
        List<String> list = new ArrayList<>(len * count);
        for (int i = 0; i < len; i++) {
            char ch = ALPHA.charAt(i);
            for (int j = 0; j < count; j++) {
                list.add(String.valueOf(ch));
            }
        }
        // 打乱文件元素
        Collections.shuffle(list);
        for (int i = 0; i < 26; i++) {
            String filename = "tmp/" + (i + 1) + ".txt";
            File file = new File(filename);
            // 确保目录存在
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
                String collect = String.join("\n", list.subList(i * count, (i + 1) * count));
                out.print(collect);
            }
        }
    }

}
