package top.ytazwc.poi.utils;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFColor;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author 花木凋零成兰
 * @title ExcelUtil
 * @date 2024-12-12 21:20
 * @package top.ytazwc.poi.utils
 * @description
 */
public class ExcelUtil {

    public final static String[] STUDENT_INFO = {
            "序号",
            "学生ID",
            "学生姓名",
            "学生年龄",
            "学生性别",
            "学生电子邮件",
            "学生专业",
            "学生入学日期",
            "学生预计毕业年份"
    };

    public final static String[] TEACHER_INFO = {
            "序号",
            "教师ID",
            "教师姓名",
            "教师教授科目",
            "教师电子邮件",
            "教师雇佣日期",
            "教师薪资"
    };

    // 单元格样式-绿色
    public static CellStyle cellStyle1(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        Color color = getColor(0, 255, 0);
        cellStyle.setFillForegroundColor(color);
        // 设置颜色填充
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return cellStyle;
    }

    // 单元格样式-橙色
    public static CellStyle cellStyle2(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        Color color = getColor(255, 165, 0);
        // 涉案只单元格样式的填充 前景色
        cellStyle.setFillForegroundColor(color);
        // 设置颜色填充
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return cellStyle;
    }

    // 根据rgb获取颜色
    public static Color getColor(int r, int g, int b) {
        return new XSSFColor(new java.awt.Color(r, g, b), null);
    }

    public static void writeToResp(SXSSFWorkbook workbook, HttpServletResponse response, String filename) throws UnsupportedEncodingException {
        // 设置响应
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//        response.setHeader("Content-Disposition", "attachment; filename=" + filename + ".xlsx");
        // 解决乱码
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "utf-8") + ".xlsx");

        try (ServletOutputStream outputStream = response.getOutputStream();) {
            workbook.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (workbook != null) {
                // 清楚缓存
                workbook.dispose();
            }
        }

    }


}
