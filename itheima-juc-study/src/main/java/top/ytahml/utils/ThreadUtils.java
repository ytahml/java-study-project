package top.ytahml.utils;

import lombok.SneakyThrows;

/**
 * @author 花木凋零成兰
 * @title ThreadUtils
 * @date 2025-06-08 12:39
 * @package top.ytahml.utils
 * @description
 */
public class ThreadUtils {

    @SneakyThrows
    public static void sleep(long millions){
        Thread.sleep(millions);
    }

}
