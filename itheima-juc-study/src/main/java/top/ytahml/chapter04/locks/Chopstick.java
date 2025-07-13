package top.ytahml.chapter04.locks;

import lombok.*;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 花木凋零成兰
 * @title Chopstick
 * @date 2025-07-13 15:16
 * @package top.ytahml.chapter04.locks
 * @description 筷子
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Chopstick extends ReentrantLock {

    private String name;

}
