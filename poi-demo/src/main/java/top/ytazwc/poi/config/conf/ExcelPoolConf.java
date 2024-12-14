package top.ytazwc.poi.config.conf;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 花木凋零成兰
 * @title ExcelPoolConf
 * @date 2024-12-14 11:37
 * @package top.ytazwc.poi.config.conf
 * @description
 */
@Data
@Component
public class ExcelPoolConf {

    @Value("${excel.pool.max-pool-size}")
    private Integer maxPoolSize;

    @Value("${excel.pool.core-pool-size}")
    private Integer corePoolSize;

    @Value("${excel.pool.keep-alive-time}")
    private Integer keepAliveTime;

    @Value("${excel.pool.max-queue-size}")
    private Integer maxQueueSize;

    @Value("${excel.pool.name-prefix}")
    private String namePrefix;

}
