package com.asa.base;

/**
 * @Description
 * @Date 2019-08-14 10:47
 * @Author Asa
 * @Version 1.0
 **/
public class EscapeAnalysisDemo {
    private static class Foo {
        private int x;
        private static int counter;

        public Foo() {
            x = (++counter);
        }
    }

    public static void main(String[] args) {
        long start = System.nanoTime();
        for (int i = 0; i < 1000 * 1000 * 10; ++i) {
            Foo foo = new Foo();
        }
        long end = System.nanoTime();
        System.out.println("Time cost is " + (end - start));
    }
}
