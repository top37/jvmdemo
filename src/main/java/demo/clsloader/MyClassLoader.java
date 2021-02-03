package demo.clsloader;

import java.io.*;

/**
 * @author 单强 2021年02月03日
 * 自定义类加载器
 */
public class MyClassLoader extends ClassLoader{

    /**
     * 加载器的名字
     */
    private String name;
    /**
     * 加载路径
     */
    private String path;

    @Override
    protected Class<?> findClass(String className) {
        byte[] data = this.loadClassData(className);
        //将字节数组转换成Class对象
        return this.defineClass(className, data, 0, data.length);
    }

    private byte[] loadClassData(String className) {
        InputStream inputStream;
        byte[] data = null;

        try ( ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream() ) {
            className = className.replace('.', '/');
            inputStream = new FileInputStream(new File(path + "/" + className + ".class"));

            int ch = 0;
            while (-1 != (ch = inputStream.read())) {
                byteArrayOutputStream.write(ch);
            }
            data = byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public MyClassLoader(String name, String path) {
//        super(); //让系统类加载器成为该类加载器的父类；补充一下基础知识，如果子类没有调用父类的有参构造方法，则默认会调用无参构造方法super()，所以这一行可以注释掉
        this.name = name;
        this.path = path;
    }

    public MyClassLoader(String name, String path, ClassLoader parent) {
        //显示指定该类加载器的的父加载器
        super(parent);
        this.name = name;
        this.path = path;
    }

}
