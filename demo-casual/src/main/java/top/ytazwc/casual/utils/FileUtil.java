package top.ytazwc.casual.utils;

/**
 * @author 花木凋零成兰
 * @title FileUtil
 * @date 2024-12-26 22:48
 * @package top.ytazwc.casual.utils
 * @description
 */
public final class FileUtil {

    /**
     * Windows 分隔符
      */
    public final static String WINDOWS_SEPARATOR = "\\";

    /**
     * Linux 分隔符
     */
    public final static String LINUX_SEPARATOR = "/";

    /**
     * 点号
     */
    public final static String PERIODS = ".";

    /**
     * markdown 文件后缀
     */
    public final static String MD_SUFFIX = ".md";

    /**
     * 将windows路径换成Linux路径
     */
    public static String replaceLinux(String windowsPath) {
        return windowsPath.replace(WINDOWS_SEPARATOR, LINUX_SEPARATOR);
    }

}
