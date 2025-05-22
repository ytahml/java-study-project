package top.ytazwc.poi.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

/**
 * @author 花木凋零成兰
 * @title Teacher
 * @date 2024-12-10 23:13
 * @package top.ytazwc.poi.entity
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("teachers")
public class Teacher implements Serializable {

    @TableField("id")
    private Integer id ;
    /** 教师姓名 */
    private String name ;
    /** 教师教授科目 */
    private String subject ;
    /** 教师电子邮件 */
    private String email ;
    /** 教师雇佣日期 */
    private Date hireDate ;
    /** 教师薪资 */
    private Double salary ;
}
