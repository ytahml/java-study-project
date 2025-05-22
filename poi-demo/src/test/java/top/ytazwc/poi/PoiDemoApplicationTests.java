package top.ytazwc.poi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class PoiDemoApplicationTests {

    // 路径
    private final static String filePath = "E:\\workspace\\Java\\workspace\\java-study-project\\";

    @Test
    void poiExcelTest_03() {

        try (
                // 1、创建一个工作簿
                Workbook workbook = new HSSFWorkbook();
                FileOutputStream fileOutputStream = new FileOutputStream(filePath + "poi-excel-03.xls");
        ) {
            // 2. 创建一个工作表
            Sheet sheet = workbook.createSheet("第一个工作表");
            int n = 3;
            // 行列表
            List<Row> rowList = new ArrayList<>();
            for (int i = 0; i < n; ++ i) {
                rowList.add(sheet.createRow(i));
            }
            // 创建单元格
            int m = 2;
            List<List<Cell>> cells = new ArrayList<>();
            rowList.forEach(row -> {
                List<Cell> cellList = new ArrayList<>();
                for (int i = 0; i < m; ++ i) {
                    cellList.add(row.createCell(i));
                }
                cells.add(cellList);
            });

            // 设置单元格值
            cells.get(0).get(0).setCellValue("个人公众号");
            cells.get(0).get(1).setCellValue("YTAZWC");
            cells.get(1).get(0).setCellValue("个人博客");
            cells.get(1).get(1).setCellValue("ytazwc.top");
            cells.get(2).get(0).setCellValue("当前时间");
            cells.get(2).get(1).setCellValue("ssss");

            // 输出
            workbook.write(fileOutputStream);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void poiExcelTest_07() {

        try (
                // 1、创建一个工作簿
                Workbook workbook = new XSSFWorkbook();
                FileOutputStream fileOutputStream = new FileOutputStream(filePath + "poi-excel-07.xlsx");
        ) {
            // 2. 创建一个工作表
            Sheet sheet = workbook.createSheet("第一个工作表");
            int n = 3;
            // 行列表
            List<Row> rowList = new ArrayList<>();
            for (int i = 0; i < n; ++ i) {
                rowList.add(sheet.createRow(i));
            }
            // 创建单元格
            int m = 2;
            List<List<Cell>> cells = new ArrayList<>();
            rowList.forEach(row -> {
                List<Cell> cellList = new ArrayList<>();
                for (int i = 0; i < m; ++ i) {
                    cellList.add(row.createCell(i));
                }
                cells.add(cellList);
            });

            // 设置单元格值
            cells.get(0).get(0).setCellValue("个人公众号");
            cells.get(0).get(1).setCellValue("YTAZWC");
            cells.get(1).get(0).setCellValue("个人博客");
            cells.get(1).get(1).setCellValue("ytazwc.top");
            cells.get(2).get(0).setCellValue("当前时间");
            cells.get(2).get(1).setCellValue("ssss");

            // 输出
            workbook.write(fileOutputStream);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 测试大量数据的导出
    @Test
    void bigDateExcelTest() {
        try (
//                Workbook workbook = new XSSFWorkbook();
                // 使用新类 提高导出速度
                SXSSFWorkbook workbook = new SXSSFWorkbook();
                FileOutputStream fileOutputStream = new FileOutputStream(filePath + "poi-excel-big.xlsx");
        ) {
            Sheet sheet = workbook.createSheet("大文件导出测试");
            long start = System.currentTimeMillis();

            for (int row = 0; row < 65536; ++ row) {
                Row r = sheet.createRow(row);
                for (int cell = 0; cell < 100; ++ cell) {
                    Cell c = r.createCell(cell);
                    c.setCellValue("{" + (row+1) + ", " + (cell+1) + "}");
                }
            }
            workbook.write(fileOutputStream);

            // 清理临时文件
            workbook.dispose();

            long end = System.currentTimeMillis();
            System.out.println("耗时: " + (double)(end-start)/1000 + "秒");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
