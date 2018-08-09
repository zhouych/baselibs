package com.zyc.baselibs.commons;

import java.util.Date;

import org.junit.Test;

public class ReflectUtilsTest<B> {

	@Test
	public void clazzInstanceTest() {
		B b = ReflectUtils.clazzInstance("com.zyc.baselibs.commons.C$A$B", new Object[] { "a", "b", new Integer("1").intValue(), new Date() });
		System.out.println(b.toString());

		D d = ReflectUtils.clazzInstance("com.zyc.baselibs.commons.D", new Object[] { "a", "b", new Integer("1").intValue(), new Date() });
		System.out.println(d.toString());
	}
}

class D {
	private String b;
	private int count;
	private Date date;

	public void setDate(Date date) {
		this.date = date;
	}

	public D() {
	}

	public D(String a, String b, int count, Date date) {
		this.b = b;
		this.count = count;
		this.date = date;
	}

	public D(String a, String b, int count) {
		super();
		this.b = b;
		this.count = count;
		this.date = new Date();
	}

	public D(Date date, String a, String b, int count) {
		super();
		this.b = b;
		this.count = count;
		this.date = date;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Date getDate() {
		return date;
	}
}

class C {
	public class A {
		public class B {
			private String b;
			private int count;
			private Date date;

			public void setDate(Date date) {
				this.date = date;
			}

			public B() {
			}

			public B(String a, String b, int count, Date date) {
				this.b = b;
				this.count = count;
				this.date = date;
			}

			public B(String a, String b, int count) {
				super();
				this.b = b;
				this.count = count;
				this.date = new Date();
			}

			public B(Date date, String a, String b, int count) {
				super();
				this.b = b;
				this.count = count;
				this.date = date;
			}

			public String getB() {
				return b;
			}

			public void setB(String b) {
				this.b = b;
			}

			public int getCount() {
				return count;
			}

			public void setCount(int count) {
				this.count = count;
			}

			public Date getDate() {
				return date;
			}
		}
	}
}