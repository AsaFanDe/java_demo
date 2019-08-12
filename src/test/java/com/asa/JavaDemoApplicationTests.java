package com.asa;

import org.junit.Test;


public class JavaDemoApplicationTests {

	@Test
	public void contextLoads() {
		System.out.println(this.tryFinally());
	}

	private int tryFinally() {
		int i = 0;
		try {
			i++;
			return i;
		}finally {
			System.out.println("finally block");
			i++;
		}
	}

	@Test
	public void test() {
		float f = 2.2f;
		double d = (double) f;
		System.out.println(f);
		System.out.println(d);
	}

}
