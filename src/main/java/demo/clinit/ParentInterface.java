package demo.clinit;

/**
 * 接口比较自我：
 * 1.初始化一个接口时，不会初始化它的父接口
 * 2.只有当程序首次使用接口里面的 field or method 时，才会导致接口初始化
 */
public interface ParentInterface {
}
