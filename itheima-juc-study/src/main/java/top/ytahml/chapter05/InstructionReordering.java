package top.ytahml.chapter05;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

/**
 * @author 花木凋零成兰
 * @title InstructionReordering
 * @date 2025-08-10 16:29
 * @package top.ytahml.chapter05
 * @description 指令重排测试
 * 采用jcstress框架进行测试
 */
@JCStressTest
@Outcome(id = {"1", "4"}, expect = Expect.ACCEPTABLE, desc = "ok")
@Outcome(id = "0", expect = Expect.ACCEPTABLE_INTERESTING, desc = "!!!!")
@State
public class InstructionReordering {

    int num = 0;
    // 禁用指令重排
    volatile boolean ready = false;

    @Actor
    public void actor1(I_Result r) {
        if (ready) {
            r.r1 = num + num;
        } else {
            r.r1 = 1;
        }
    }
    @Actor
    public void actor2(I_Result r) {
        num = 2;
        // ready 之前的代码禁止重排序
        // 读屏障：在这之后的共享变量的读操作都从主存中读取；
        ready = true;
        // 写屏障：在这之前的共享变量的写操作都同步到共享变量中；
    }

}
