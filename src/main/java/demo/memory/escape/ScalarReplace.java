package demo.memory.escape;

/**
 * https://juejin.cn/post/6877004782130151438
 * 标量替换：进一步减少gc
 *
 * 标量替换为：
 * public String noEscape(){
 *      int age=26;
 *      String name="TomCoding noEscape";
 *      return name;
 * }
 */
public class ScalarReplace {

    public Person p;
    /**
     * 发生逃逸，对象被返回到方法作用域以外，被方法外部，线程外部都可以访问
     */
    public void escape(){
        p = new Person(26, "TomCoding escape");
    }

    /**
     * 不会逃逸，对象在方法内部
     */
    public String noEscape(){
        Person person = new Person(26, "TomCoding noEscape");
        return person.name;
    }

    static class Person {
        public int age;
        public String name;

        public Person(int age, String name) {
            this.age = age;
            this.name = name;
        }
    }
}
