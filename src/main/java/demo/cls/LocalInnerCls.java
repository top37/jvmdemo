package demo.cls;


/**
 * https://www.cnblogs.com/yangming1996/p/8869081.html
 * 内部类的意义：接口 + 内部类 = 多继承
 *
 * 局部内部类：方法体中定义类
 *
 * 与局部变量类似，在局部内部类前不加修饰符public或private，其范围为定义它的代码块。
 * 注意：局部内部类中不可定义静态变量，可以访问外部类的局部变量(即方法内的变量)，但是变量必须是final的。
 */
public class LocalInnerCls {
    public String getA(){
        //在方法体中定义类
        class A{
            String a = "abc";
        }
        A a = new A();
        return a.a;
    }

    public static String getStaticA(){
        class A{
            String a = "static - abc";
        }
        A a = new A();
        return a.a;
    }


    public static void main(String[] args) {

        System.out.println(new LocalInnerCls().getA());
        System.out.println(getStaticA());
    }
}
