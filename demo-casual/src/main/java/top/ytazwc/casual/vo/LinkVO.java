package top.ytazwc.casual.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 花木凋零成兰
 * @title LinkVO
 * @date 2024-12-19 22:53
 * @package top.ytazwc.casual.vo
 * @description
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LinkVO {


    /**
     * 标题
     */
    private String text;

    /**
     * 路径
     */
    private String link;

}
