package top.ytazwc.casual.service;

import top.ytazwc.casual.vo.LinkVO;

import java.util.List;

/**
 * @author 花木凋零成兰
 * @title CommonService
 * @date 2024-12-19 23:04
 * @package top.ytazwc.casual.service
 * @description
 */
public interface CommonService {

    /**
     * 根据文件路径 生成对应 link
     * @param path 路径
     * @return 结果列表
     */
    List<LinkVO> buildLink(String path);

}
