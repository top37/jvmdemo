package demo.memory.heap;

import java.util.ArrayList;
import java.util.List;

/**
 * 分析一波：
 * 首先，list肯定是可以做Root的
 * 里面的new OOMObject()肯定是不会被回收的
 * aliugen线程因OOM挂了，而后释放了其持有的内存；
 * 主线程运行是不消耗过多资源
 */
public class OomStillRunMain {

    static class OOMObject {
    }

    // 为快速发生oom，设置堆大小; VM args: -Xms20m -Xmx20m
    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            List<OOMObject> list = new ArrayList<>();
            while (true) {
                list.add(new OOMObject());
            }
        },"aliugen").start();

        while (true) {
            System.out.println(Thread.currentThread().getName() + " continuing...");
            Thread.sleep(1000L);
        }
    }
}
