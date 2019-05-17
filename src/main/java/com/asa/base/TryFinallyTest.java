package com.asa.base;

/**
 * @Description 判断return 和 finally哪个先执行
 * 结果是:先执行return,待finally块执行后再返回数据，finally块不影响返回结果
 * 如果finally带return语句，则会覆盖之前的return
 * @Date 2019-05-05 17:09
 * @Author Asa
 * @Version 1.0
 **/
public class TryFinallyTest {

    private int tryFinally() {
        int i = 0;
        try {
            return ++i;
        }finally {
            System.out.println("finally block");
            i++;
            System.out.println("finally: " + i);
        }
    }

    public static void main(String[] args) {
        TryFinallyTest t = new TryFinallyTest();
        System.out.println(t.tryFinally());
    }

    /** ++i 和 i++的区别
     * 1、首先，单独拿出来说++i和i++，意思都是一样的，就是i=i+1。
     * 2、如果当做运算符来说，就是a=i++或者a=++i这样的形式。情况就不一样了。
     * 先说a=i++，这个运算的意思是先把i的值赋予a，然后在执行i=i+1；
     * 而a=++i，这个的意思是先执行i=i+1，然后在把i的值赋予a；
     * 举个例子来说，如果一开始i=4。
     * 那么执行a=i++这条语句之后，a=4，i=5；
     * 那么执行a=++i这条语句之后，i=5，a=5；
     * 同理，i--和--i的用法也是一样的。
     */
}
