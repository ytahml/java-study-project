package top.ytahml.chapter07;

/**
 * final原理：变量赋值指令后加入 写屏障
 *
 * @author 花木凋零成兰
 * @since 2025/9/30 下午5:10
 */
public class FinalTest {

    final static int A = 10;
    final static int B = Short.MAX_VALUE;

    final int a = 20;
    final int b = Integer.MAX_VALUE;

    private static class UseFinal1 {
        public void test() {
            System.out.println(FinalTest.A);
            System.out.println(FinalTest.B);
            System.out.println(new FinalTest().a);
            System.out.println(new FinalTest().b);
        }
    }

    private static class UseFinal2 {
        public void test() {
            System.out.println(FinalTest.A);
        }
    }

}
