package demo.clsloader;

import java.io.File;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;


/**
 * https://blog.csdn.net/m0_37962779/article/details/78279721
 *
 * Set instance null and execute gc!
 * GC回收了:Instance of Foo
 * Set class null and execute gc!
 * Set classloader null and execute gc!
 * GC回收了:Class classloader.test.Foo
 * GC回收了:ClassLoader URLClassLoader
 */
public class SimpleMonitorClassLoader {
    public static void main(String args[]) throws Exception{
        final ReferenceQueue<Object> rq = new ReferenceQueue<Object>();
        final Map<Object, Object> map = new HashMap<>();
        Thread thread = new Thread(() -> {
            try {
                WeakReference<byte[]> k;
                while((k = (WeakReference) rq.remove()) != null) {
                    System.out.println("GC回收了:" + map.get(k));
                }
            } catch(InterruptedException e) {
                //结束循环
            }
        });
        thread.setDaemon(true);
        thread.start();

        ClassLoader cl = newLoader();
        Class cls = cl.loadClass("demo.clsloader.Foo");
        Object obj = cls.newInstance();


        Object value = new Object();

        WeakReference<ClassLoader> weakReference = new WeakReference<ClassLoader>(cl, rq);
        map.put(weakReference, "ClassLoader URLClassLoader");
        WeakReference<Class> weakReference1 = new WeakReference<Class>(cls, rq);
        map.put(weakReference1, "Class classloader.test.Foo");
        WeakReference<Object> weakReference2 = new WeakReference<Object>(obj, rq);
        map.put(weakReference2, "Instance of Foo");

        obj=null;
        System.out.println("Set instance null and execute gc!");
        System.gc();
        Thread.sleep(3000);
        cls=null;
        System.out.println("Set class null and execute gc!");
        System.gc();
        Thread.sleep(3000);
        cl=null;
        System.out.println("Set classloader null and execute gc!");
        System.gc();
        Thread.sleep(3000);
    }

    static URLClassLoader newLoader() throws Exception{
        URL url = new File("/Users/mac/Documents/jvmdemo/target/classes").toURI().toURL();
        URLClassLoader ucl = new URLClassLoader(new URL[] {url}, null);
        return ucl;
    }
}
