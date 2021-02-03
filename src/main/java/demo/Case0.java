package demo;

import java.util.concurrent.TimeUnit;


/**
 * asm示例：asm 修改字节啊
 */
public class Case0 {
    public void method0() throws InterruptedException {

        long start = System.currentTimeMillis();
        System.out.println("------ method0 ------");
        TimeUnit.SECONDS.sleep(3);

        System.out.println("消耗时间:["+ (System.currentTimeMillis() - start) + "]ms");
    }
}
