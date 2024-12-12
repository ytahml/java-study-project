package top.ytazwc.poi.service.impl;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.ytazwc.poi.entity.Student;
import top.ytazwc.poi.entity.Teacher;
import top.ytazwc.poi.service.ExcelService;
import top.ytazwc.poi.service.StudentService;
import top.ytazwc.poi.service.TeacherService;
import top.ytazwc.poi.utils.ExcelUtil;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 花木凋零成兰
 * @title ExcelServiceImpl
 * @date 2024-12-12 20:49
 * @package top.ytazwc.poi.service.impl
 * @description
 */
@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Override
    public boolean export(HttpServletResponse response) {
        boolean result = true;
        // 创建Excel表
        try (SXSSFWorkbook workbook = new SXSSFWorkbook()) {
            // 创建 Sheet
            SXSSFSheet student = workbook.createSheet("学生");
            SXSSFSheet teacher = workbook.createSheet("老师");

            // 构建 Excel
            boolean stuResult = buildStudent(workbook, student);
            boolean teaResult = buildTeacher(workbook, teacher);

            ExcelUtil.writeToResp(workbook, response, "测试");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    // 构建学生 Sheet
    private boolean buildStudent(Workbook workbook, Sheet sheet) {
        boolean result = true;
        // 查询需要的数据
        List<Student> list = studentService.lambdaQuery().last("LIMIT 100000").list();
        // 表头
        final String[] header = ExcelUtil.STUDENT_INFO;
        // 行号
        int rowIndex = 0;
        // 列数
        int colCount = header.length;
        // 获取表头样式
        CellStyle cellStyle = ExcelUtil.cellStyle1(workbook);

        // 初始化表头
        Row row = sheet.createRow(rowIndex ++);
        for (int i = 0; i < colCount; ++ i) {
            Cell cell = row.createCell(i);
            cell.setCellValue(header[i]);
            cell.setCellStyle(cellStyle);
        }

        // 初始化数据
        for (Student stu : list) {
            // 列号
            int colIndex = 0;
            row = sheet.createRow(rowIndex);

            // 序号
            Cell cell = row.createCell(colIndex ++);
            cell.setCellValue(rowIndex ++);

            // 学生 id
            cell = row.createCell(colIndex ++);
            cell.setCellValue(stu.getId());

            // 学生姓名
            cell = row.createCell(colIndex ++);
            cell.setCellValue(stu.getName());

            // 学生性别
            cell = row.createCell(colIndex ++);
            cell.setCellValue(stu.getGender());

            // 学生电子邮件
            cell = row.createCell(colIndex ++);
            cell.setCellValue(stu.getEmail());

            // 学生专业
            cell = row.createCell(colIndex ++);
            cell.setCellValue(stu.getMajor());

            // 学生入学日期
            cell = row.createCell(colIndex ++);
            cell.setCellValue(stu.getEnrollmentDate());

            // 学生预计毕业年份
            cell = row.createCell(colIndex ++);
            cell.setCellValue(stu.getGraduationYear());
        }


        return result;
    }

    // 构建老师 Sheet
    private boolean buildTeacher(Workbook workbook, Sheet sheet) {
        boolean result = true;
        // 查询数据
        List<Teacher> list = teacherService.lambdaQuery()
                .last("LIMIT 100000")
                .list();
        // 获取表头
        final String[] header = ExcelUtil.TEACHER_INFO;
        CellStyle cellStyle = ExcelUtil.cellStyle2(workbook);
        int rowIndex = 0;
        int colCount = header.length;
        Row row = sheet.createRow(rowIndex ++);
        for (int i = 0; i < colCount; ++ i) {
            Cell cell = row.createCell(i);
            cell.setCellValue(header[i]);
            cell.setCellStyle(cellStyle);
        }

        for (Teacher tea : list) {
            int colIndex = 0;
            row = sheet.createRow(rowIndex);

            Cell cell = row.createCell(colIndex++);
            cell.setCellValue(rowIndex ++);

            cell = row.createCell(colIndex ++);
            cell.setCellValue(tea.getId());


        }

        return result;
    }

}
