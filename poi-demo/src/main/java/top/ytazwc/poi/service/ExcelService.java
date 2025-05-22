package top.ytazwc.poi.service;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 花木凋零成兰
 * @title ExcelService
 * @date 2024-12-12 20:48
 * @package top.ytazwc.poi.service
 * @description
 */
public interface ExcelService {

    boolean export(HttpServletResponse response);

}
