package com.nowcoder.community;

import org.junit.Test;

public class ByteCodeTest {

    @Test
    public void test05() {
        Integer i1 = 10;
        Integer i2 = 10;
        System.out.println(i1 == i2); // true

        Integer i3 = 128;
        Integer i4 = 128;
        System.out.println(i3 == i4); // false

        // TODO 128之前和之后的区别？ 包装类对象的缓存问题
        //  就是说Integer内部有一个 静态内部类 IntegerCache，如果Integer的值在 [-128,127] 之间，
        //  Integer 会将其赋值为（指向）IntegerCache 的int数组对应的值，而如果不在cache范围就要新创建对象。
        //  映射到局部变量表中，i1和i2指向的是 IntegerCache 数组里面的同一个值，而i3和i4不是

        Boolean b1 = true;
        Boolean b2 = true;
        System.out.println(b1 == b2);

    }
    // TODO 关于字节码文件的一些问题
    //  bipush 10  把操作数10放到操作数栈中。
    //  操作数栈可以想象成下面这样的一个数组吗？（不对！横着的是局部变量表）


    // --------------------------------------
    // |   |   |   |   |  ...   |   |   |   |
    // --------------------------------------


    @Test
    public void test06() {
        for (int i = 0; i < 300; i++) {
            Integer t1 = i;
            Integer t2 = i;
            System.out.println("i = " + i + ": " + (t1 == t2));
        }
    }

    @Test
    public void test07() {
        float f1 = 1.22f;
        float f2 = 1.22f;
        System.out.println(f1 == f2);
        // FIXME 为啥是true？没有缓存对象？那比较不应该是false吗？
    }

    @Test
    public void test08() {
        String s1 = new String("hello") + new String("world");
        String s2 = "helloworld";
        System.out.println(s1 == s2); // false
    }

    @Test
    public void test09() {
        String s1 = new String("hello") + new String("world");
        s1.intern();
        String s2 = "helloworld";
        System.out.println(s1 == s2); // true
        // jdk6和jdk8还不一样，如果用jdk8执行时true，jdk6执行为false
        // NOTE String声明的字面量数据都存放在字符串常量池中
        //  jdk 6中字符串常量池存放在方法区（即永久代中）
        //  jdk7 即以后字符串常量池存放在堆空间
    }

    @Test
    public void test10() {
        String s1 = new String("hello") + new String("world");
        String s2 = "helloworld";
        s1.intern();
        System.out.println(s1 == s2); // false
    }


}
