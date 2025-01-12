package top.ytazwc.for_map;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 花木凋零成兰
 * @title MatchEnum
 * @date 2025-01-12 16:32
 * @package top.ytazwc.for_map
 * @description
 */
@Getter
@AllArgsConstructor
public enum MatchEnum {

    A(1),
    B(2),
    C(3),
    D(4),
    E(5),
    F(6),
    G(7),
    H(8),
    I(9),
    J(10),

    NULL(-1)
    ;

    private final Integer value;

    public static MatchEnum matchWithFor(Integer value) {
        for (MatchEnum matchEnum : values()) {
            if (matchEnum.getValue().equals(value)) {
                return matchEnum;
            }
        }
        return NULL;
    }

    private static final Map<Integer, MatchEnum> enumMap = new HashMap<>();

    static {
        for (MatchEnum value : values()) {
            enumMap.put(value.getValue(), value);
        }
    }

    public static MatchEnum matchWithMap(Integer value) {
        MatchEnum result = enumMap.get(value);
        return result == null ? NULL : result;
    }

}
