package demo.clinit;

public class Son extends Parent{
    static{
        System.out.println(" init class Son ");
    }

    /**
     * 顺序必须在使用之前
     * 否则，编译报错：illegal forward reference
     */
    static String name = "aqiang";

    static{
        System.out.println(" field name =  " + name);
//        System.out.println(" field name =  " + age);
    }

//    static int age = 10;

}
