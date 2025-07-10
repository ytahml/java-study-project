package top.ytahml.chapter04.asynchronous;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author 花木凋零成兰
 * @title Message
 * @date 2025-07-09 20:41
 * @package top.ytahml.chapter04.asynchronous
 * @description
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public final class Message<T> {

    private int id;
    private T value;

}
