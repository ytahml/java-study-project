package top.ytahml.chapter07.pool;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import top.ytahml.utils.ThreadUtils;

import java.sql.Connection;

/**
 * 请添加类的描述信息.
 *
 * @author 花木凋零成兰
 * @since 2025/9/30 下午4:50
 */
public class PoolTest {

    Pool pool;

    @Before
    public void setUp() {
        pool = new Pool(2);
    }

    @After
    public void tearDown() {
//        pool = null;
    }

    @Test
    public void test() {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                Connection conn = pool.borrow();
                ThreadUtils.sleep(1000);
                pool.free(conn);
            }, "t" + (i+1)).start();
        }

        ThreadUtils.sleep(10000);
    }

}