package top.ytazwc.poi;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.ytazwc.poi.entity.Student;
import top.ytazwc.poi.mapper.StudentMapper;
import top.ytazwc.poi.service.StudentService;

import java.util.List;

/**
 * @author 花木凋零成兰
 * @title MpTest
 * @date 2024-12-12 22:17
 * @package top.ytazwc.poi
 * @description
 */
@SpringBootTest
public class MpTest {

    @Autowired
    private StudentService service;

    @Autowired
    private StudentMapper mapper;

    @Test
    void limitTest1() {
        Page<Student> page = new Page<>(1, 1000);
        IPage<Student> res = service.page(page, null);
        List<Student> records = res.getRecords();
        System.out.println(records.size());
    }

    @Test
    void limitTest2() {
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        Page<Student> page = new Page<>(1, 1000);
        Page<Student> studentPage = mapper.selectPage(page, queryWrapper);

    }

    @Test
    void limitTest3() {
        List<Student> list = service.lambdaQuery().last("LIMIT 1000").list();
        System.out.println(list.size());
    }

}
