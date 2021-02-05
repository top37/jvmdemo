package demo.memory.escape;

import utils.TimeTools;

/**
 * http://www.hollischuang.com/archives/2398
 * 目的：减少gc，配合标量替换效果更佳；
 *
 * 逃逸：对象是否只存在于方法块中，
 * 若是，则不逃逸，对象【可能】在栈上生成；
 * 否则，逃逸，对象在堆上产生
 *
 * Q：逃逸分析能不能在编译期（javac）进行？难点在哪儿？
 * A：能；有不少除开 hotSpot VM 的编译器是静态编译时做的逃逸分析；
 * 难点：多态；https://www.zhihu.com/question/27963717
 * A【ext】：
 * 最主要的一个技术难点就是Java蛋疼的分离编译（separate compilation）和动态类加载（dynamic class loading）/动态链接（dynamic linking）。这就回到了前面几个回答所提到的：不知道运行时会加载并链接上什么代码；
 * 通俗的讲：
 * 简单来说是可以的，但是Java的分离编译和动态加载使得前期的静态编译的逃逸分析比较困难或收益较少，所以目前Java的逃逸分析只发在JIT的即时编译中，因为收集到足够的运行数据JVM可以更好的判断对象是否发生了逃逸。
 * 涉及概念：
 * 虚方法：Java的非私有实例方法默认是虚方法
 *
 * 通过参数来 开启 / 关闭 逃逸分析
 * -XX:-DoEscapeAnalysis -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError
 */
public class Escape {
    public static void main(String[] args) {
        long a1 = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            alloc();
        }
        // 查看执行时间
        long a2 = System.currentTimeMillis();
        System.out.println("cost " + (a2 - a1) + " ms");



        System.gc();

        // 为了方便查看堆内存中对象个数，线程sleep
        TimeTools.stop(Integer.MAX_VALUE);
    }

    /**
     * alloc方法中定义了User对象，但是并没有在方法外部引用他。
     * 也就是说:
     * 这个对象并不会逃逸到alloc外部。经过JIT的逃逸分析之后，就可以对其内存分配进行优化。
     */
    private static void alloc() {
        User user = new User();
    }

    static class User {

    }
}
