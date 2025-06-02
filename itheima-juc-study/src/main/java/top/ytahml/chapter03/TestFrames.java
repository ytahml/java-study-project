package top.ytahml.chapter03;

/**
 * @author 花木凋零成兰
 * @title TestFrames
 * @date 2025-06-02 11:03
 * @package top.ytahml.chapter03
 * @description 测试栈帧
 */
public class TestFrames {

    public static void main(String[] args) {

        method1(10);

    }

    private static void method1(int x) {
        int y = x + 1;
        Object m = method2();
        System.out.println(m);
    }

    private static Object method2() {
        Object n = new Object();
        return n;
    }

}
