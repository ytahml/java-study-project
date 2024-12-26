package top.ytazwc.casual.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ytazwc.casual.service.CommonService;
import top.ytazwc.casual.vo.LinkVO;
import top.ytazwc.common.result.Result;

import java.util.List;

/**
 * @author 花木凋零成兰
 * @title CommonController
 * @date 2024-12-19 22:03
 * @package top.ytazwc.casual.controller
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private CommonService service;

    @GetMapping("/build-link")
    public Result<List<LinkVO>> buildLink(String path) {
        List<LinkVO> list = service.buildLink(path);
        return Result.success(list);
    }

}
