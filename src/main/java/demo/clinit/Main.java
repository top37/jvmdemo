package demo.clinit;

import demo.clsloader.MyClassLoader;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        System.out.println("--------------");

        MyClassLoader myClassLoader = new MyClassLoader("myClzLoader","/Users/mac/Documents/jvmdemo/outclass");

        Class cls = myClassLoader.loadClass("demo.clinit.Son");
        System.out.println("--------------");
        cls.newInstance(); // 可初始化
//        Son s = (Son)cls.newInstance(); // Son的起点低，当前类的【定义类加载器：AppClassLoader】够不到，需要使用【线程上下文类加载器】去破坏双亲委派

        System.out.println("--------------");


    }

}
