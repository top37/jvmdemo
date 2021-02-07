package demo.memory.obj;

import org.openjdk.jol.info.ClassLayout;

/**
 * 【未果】
 * 试一下对象头的指针是否是引用值
 *
 * 没有指针的数据：
 * demo.memory.obj.ObjHeadPointMain.ObjInfo ObjHeadPointMain.objInfo       (object)
 */
public class ObjHeadPointMain {

    ObjInfo objInfo = new ObjInfo();

    public static void main(String[] args) {

        ObjHeadPointMain objHeadPointMain = new ObjHeadPointMain();
        System.out.println("objHeadPointMain-----start");
        System.out.println(ClassLayout.parseInstance(objHeadPointMain).toPrintable());
        System.out.println("objHeadPointMain.objInfo-----start");
        System.out.println(ClassLayout.parseInstance(objHeadPointMain.objInfo).toPrintable());
        System.out.println("======分割线======");
    }



    private static class ObjInfo{
        byte b;
        private int age;
        private Integer money;
        private String name;
        private String sex = "man";
    }
}
