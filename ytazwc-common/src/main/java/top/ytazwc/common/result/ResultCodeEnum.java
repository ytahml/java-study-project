package top.ytazwc.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 花木凋零成兰
 * @title ResultCodeEnum
 * @date 2024-12-26 22:07
 * @package top.ytazwc.common.result
 * @description
 */
@Getter
@AllArgsConstructor
public enum ResultCodeEnum {

    SUCCESS(200),
    FAILED(500)
    ;

    private final Integer code;

}
