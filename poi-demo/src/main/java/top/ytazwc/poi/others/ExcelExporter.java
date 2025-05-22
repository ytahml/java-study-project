package top.ytazwc.poi.others;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author 花木凋零成兰
 * @title ExcelExporter
 * @date 2024-12-09 22:35
 * @package top.ytazwc.poi
 * @description
 */
public class ExcelExporter {

    public static void exportToExcel(List<List<Object>> data, String filePath) throws IOException {
        // 创建工作簿
        Workbook workbook = new XSSFWorkbook();

        // 创建工作表
        Sheet sheet = workbook.createSheet("Sheet1");

        // 添加数据
        for (int i = 0; i < data.size(); i++) {
            Row row = sheet.createRow(i);
            List<Object> rowData = data.get(i);

            for (int j = 0; j < rowData.size(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(rowData.get(j).toString());
            }
        }

        // 保存Excel文件
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
            workbook.close();
        }
    }

    public static void setCellStyle(Cell cell, IndexedColors backgroundColor, Font font) {
        Workbook workbook = cell.getSheet().getWorkbook();
        CellStyle style = workbook.createCellStyle();

        // 设置背景色
        style.setFillForegroundColor(backgroundColor.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // 设置字体样式
        style.setFont(font);

        // 应用样式到单元格
        cell.setCellStyle(style);
    }
}
