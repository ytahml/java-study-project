package top.ytazwc.poi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.ytazwc.poi.config.conf.ExcelPoolConf;
import top.ytazwc.poi.config.factory.CustomThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 花木凋零成兰
 * @title ExcelPoolConfig
 * @date 2024-12-14 11:40
 * @package top.ytazwc.poi.config
 * @description
 */
@Configuration
public class ExcelPoolConfig {

    @Bean("excelPoolService")
    public ExecutorService excelPoolService(ExcelPoolConf conf) {
        // 创建一个 核心线程：3，最大线程数：3，队列容量：10，拒绝策略：由调用线程处理被拒绝的任务
        return new ThreadPoolExecutor(
                conf.getCorePoolSize(),
                conf.getMaxPoolSize(),
                conf.getKeepAliveTime(),
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(conf.getMaxQueueSize()),
                new CustomThreadFactory(conf.getNamePrefix()),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }

}
