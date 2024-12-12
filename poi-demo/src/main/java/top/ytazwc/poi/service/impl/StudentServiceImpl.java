package top.ytazwc.poi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.ytazwc.poi.entity.Student;
import top.ytazwc.poi.mapper.StudentMapper;
import top.ytazwc.poi.service.StudentService;

/**
 * @author 花木凋零成兰
 * @title StudentServiceImpl
 * @date 2024-12-10 23:27
 * @package top.ytazwc.poi.service.impl
 * @description
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
}
