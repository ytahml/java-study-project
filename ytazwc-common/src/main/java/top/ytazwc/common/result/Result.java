package top.ytazwc.common.result;

import lombok.AllArgsConstructor;

/**
 * @author 花木凋零成兰
 * @title Result
 * @date 2024-12-26 21:08
 * @package top.ytazwc.common.result
 * @description 结果
 */
@AllArgsConstructor
public final class Result<T> {

    private String msg;

    private Integer code;

    private T data;

    public Result() {
    }

    public static <T> Result<T> success(T data, String msg) {
        return new Result<T>(msg, ResultCodeEnum.SUCCESS.getCode(), data);
    }

    public static <T> Result<T> success(String msg) {
        return new Result<T>(msg, ResultCodeEnum.SUCCESS.getCode(), null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>("", ResultCodeEnum.SUCCESS.getCode(), data);
    }

    public static <T> Result<T> failed(T data, String msg) {
        return new Result<T>(msg, ResultCodeEnum.FAILED.getCode(), data);
    }

    public static <T> Result<T> failed(String msg) {
        return new Result<T>(msg, ResultCodeEnum.FAILED.getCode(), null);
    }

    public static <T> Result<T> failed(T data) {
        return new Result<T>("", ResultCodeEnum.FAILED.getCode(), data);
    }

}
