package demo.memory.heap;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 内存溢出时保存快照文件至指定文件夹，并通过IDEA【JProfiler】查看hprof文件
 *
 * -Xms5m -Xmx5m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/Users/mac/Documents/jvmdemo/heapdump/
 * -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+PrintHeapAtGC
 *
 * java8 & earlest: 模拟：gc debug
 * -Xloggc:/Users/mac/Documents/jvmdemo/gclog/gc.log -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+PrintHeapAtGC
 */
public class OomMain {

    /**
     * 1M
     */
    private byte[] bs = new byte[ 1024 ];

    public static void main(String[] args) throws InterruptedException {

        System.out.println("abc");

        List<OomMain> lstBs = new ArrayList<>();

        int i = 0;
        try {
            while (true) {
                ++i;
                //强引用，不会被回收
//                OomMain oom = new OomMain();
                lstBs.add(new OomMain());
                TimeUnit.SECONDS.sleep(20);

                System.out.println("i == " + i);
            }
        }catch (Throwable e){
            System.out.println("次数：" + i);
            TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        }

    }

}
