package top.ytazwc.casual.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.ytazwc.casual.service.CommonService;
import top.ytazwc.casual.utils.FileUtil;
import top.ytazwc.casual.vo.LinkVO;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author 花木凋零成兰
 * @title CommonServiceImpl
 * @date 2024-12-19 23:05
 * @package top.ytazwc.casual.service.impl
 * @description
 */
@Slf4j
@Service
public class CommonServiceImpl implements CommonService {

    @Override
    public List<LinkVO> buildLink(String path) {
        List<LinkVO> list = new ArrayList<>();
        String newPath = FileUtil.replaceLinux(path);
        // 获取相对路径
        String needPath = newPath.substring(newPath.lastIndexOf(FileUtil.LINUX_SEPARATOR)) + FileUtil.LINUX_SEPARATOR;
        log.info("新相对路径:{}", newPath);
        // 目录对象
        File directory = new File(path);
        if (!(directory.exists() && directory.isDirectory())) {
            log.error("{} 不是文件夹!!!", path);
        }
        // 遍历文件夹
        File[] files = directory.listFiles();
        if (Objects.isNull(files)) {
            return list;
        }
        for (File file : files) {
            if (file.isFile()) {
                // 文件名
                String name = file.getName();
                // 去除后缀
                String text = name.substring(0, name.lastIndexOf(FileUtil.PERIODS));
                // 获得 link
                String link = needPath + text;
                LinkVO vo = new LinkVO(text, link);
                list.add(vo);
            }
        }
        return list;
    }

}
