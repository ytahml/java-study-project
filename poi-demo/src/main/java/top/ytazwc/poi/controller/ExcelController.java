package top.ytazwc.poi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ytazwc.poi.service.ExcelService;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 花木凋零成兰
 * @title ExcelController
 * @date 2024-12-12 20:44
 * @package top.ytazwc.poi.controller
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    private ExcelService service;

    @GetMapping("/export")
    public String export(HttpServletResponse response) {
        boolean result = service.export(response);
        return "导出成功!!!";
    }

}
