package top.ytazwc.casual;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.ytazwc.casual.vo.LinkVO;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author 花木凋零成兰
 * @title FileLinkTest
 * @date 2024-12-26 22:24
 * @package top.ytazwc.casual
 * @description
 */
@SpringBootTest
public class FileLinkTest {

    @Test
    public void test() {
        String path = "E:\\Blog\\blog-vitepress\\docs\\Lua";
        String newPath = path.replace("\\", "/");

        // 获取相对路径
        String needPath = newPath.substring(newPath.lastIndexOf("/")) + "/";
        System.out.println("相对路径: " + needPath);

        // 目录对象
        File directory = new File(path);

        // 封装结果
        List<LinkVO> list = new ArrayList<>();
        // 遍历文件夹
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (Objects.isNull(files)) {
                return ;
            }
            for (File file : files) {
                if (file.isFile()) {
                    // 文件名
                    String name = file.getName();
                    // 去除后缀
                    String text = name.substring(0, name.lastIndexOf("."));
                    // 获得 link
                    String link = needPath + text;
                    LinkVO vo = new LinkVO(text, link);
                    list.add(vo);
                    System.out.println(name);
                }
            }
            System.out.println(list);
        } else {
            System.out.println("指定的路径不是一个有效的目录");
        }
    }

}
