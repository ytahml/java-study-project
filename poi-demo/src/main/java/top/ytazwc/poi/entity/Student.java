package top.ytazwc.poi.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

/**
 * @author 花木凋零成兰
 * @title Student
 * @date 2024-12-10 23:10
 * @package top.ytazwc.poi.entity
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("students")
public class Student implements Serializable {

    private Integer id ;
    /** 学生姓名 */
    private String name ;
    /** 学生年龄 */
    private Integer age ;
    /** 学生性别 */
    private String gender ;
    /** 学生电子邮件 */
    private String email ;
    /** 学生专业 */
    private String major ;
    /** 学生入学日期 */
    private Date enrollmentDate ;
    /** 学生预计毕业年份 */
    private String graduationYear ;

}
