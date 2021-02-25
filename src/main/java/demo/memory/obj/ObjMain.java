package demo.memory.obj;

import org.openjdk.jol.info.ClassLayout;

/**
 * 思维：
 * 引用【ref】也是很占用内存滴！！！
 * jvm内存最好小于32G！！！
 *
 *【默认开启】-XX:+UseCompressedOops 表示开 「启对象指针压缩 ： 压缩的是对象里面的指针」 ；
 *
 * ①一个Object对象大小是多少byte？
 * 答:16byte，压缩：有4byte用于空闲对齐，非压缩：全部使用
 *
 * ②压缩指针，又没有压缩对象，它不应该增加 | 减少 ？
 * 答：对象中的指针(field)占的空间也不小了，32位的系统中占 4byte = 32bit，最大只能表示 2^32 = 4G
 *
 * 结论：
 * 指针压缩后的性能：
 * （mem < 4G || mem > 32G） =>  【默认 不启用压缩】
 * mem ∈（4G , 32G） =>  【默认：启用压缩】
 *
 * 翻译为：
 * 如果内存在【4G以下】，JVM会直接使用低32位，【无需指针压缩】。
 * 如果内存在【32G以上】，那么JVM就【不会进行指针压缩】 (JVM在40/50G时才能达到32G的效果)。所以我们应当尽量JVM的内存在32G以下。
 * 以项目中使用Spark为例，相比较于使用一个90G的Executor，分成3个30G的Executor，内存使用率以及计算速度都能提升30%以上。
 *
 * https://blog.csdn.net/shawearn1027/article/details/107160537 【对象占用内存实测，含jvm参数】
 * https://blog.csdn.net/zc19921215/article/details/106227386
 * https://juejin.cn/post/6844903768077647880 【4G -> 32G 实现原理：讲述比较垃圾】
 * https://blog.csdn.net/liujianyangbj/article/details/108049482【4G -> 32G 实现原理：讲述的很nice】
 *
 * 使用32位的指针来指向32G的空间：
 * 32位的指针只能表示4G的空间，如果32G的空间每8个字节表示一个单元，指针指向的是每个单元，那么32位的指针就可以指向32G的空间了。
 * JVM也正是这么实现的。将最低位空间设置为初始值，其他空间以其作为offset进行表示，从而达到32位指针表示32G空间的效果。
 *
 * 我的疑问在于：压缩指针，又没有压缩对象，它不应该增加 | 减少
 * 答：对象中的指针(field|ref)占的空间也不小了，32位的系统中占 4byte = 32bit，最大只能表示 2^32 = 4G
 *
 *
 * java.lang.Object object internals:
 *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
 *       0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
 *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
 *       8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
 *      12     4        (loss due to the next object alignment)
 * Instance size: 16 bytes
 * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
 *
 * ======分割线======
 * demo.memory.obj.ObjMain$ObjInfo object internals:
 *  OFFSET  SIZE                TYPE DESCRIPTION                               VALUE
 *       0     4                     (object header)                           05 00 00 00 (00000101 00000000 00000000 00000000) (5)
 *       4     4                     (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
 *       8     4                     (object header)                           c0 01 01 f8 (11000000 00000001 00000001 11111000) (-134151744)
 *      12     4                 int ObjInfo.age                               0
 *      16     4   java.lang.Integer ObjInfo.money                             null
 *      20     4    java.lang.String ObjInfo.name                              null
 *      24     4    java.lang.String ObjInfo.sex                               (object)
 *      28     4                     (loss due to the next object alignment)
 * Instance size: 32 bytes
 * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
 */
public class ObjMain {

    public static void main(String[] args) {
        System.out.println(ClassLayout.parseInstance(new Object()).toPrintable());
        System.out.println("======分割线======");
        System.out.println(ClassLayout.parseInstance(new ObjInfo()).toPrintable());
    }



    private static class ObjInfo{
        byte b;
        private int age;
        private Integer money;
        private String name;
        private String sex = "man";
    }
}
