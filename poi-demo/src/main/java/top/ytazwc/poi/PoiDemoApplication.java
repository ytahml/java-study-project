package top.ytazwc.poi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hoshino
 */
@SpringBootApplication
@MapperScan("top.ytazwc.poi.mapper")
public class PoiDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PoiDemoApplication.class, args);
    }

}
