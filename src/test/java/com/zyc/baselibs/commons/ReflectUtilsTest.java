package com.zyc.baselibs.commons;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import com.zyc.baselibs.commons.C.A;
import com.zyc.baselibs.commons.C.A.B;

public class ReflectUtilsTest {

	@Test
	public void clazzInstanceTest() {
		C c = new C();
		A a = c.new A();
		
		B b = ReflectUtils.clazzInstance("com.zyc.baselibs.commons.C$A$B", a, new Object[] { "a", "b", new Integer("1").intValue(), new Date() });
		assertEquals(b != null && b.getClass() == B.class, true);

		B b2 = ReflectUtils.clazzInstance("com.zyc.baselibs.commons.C$A$B", null, new Object[] { "a", "b", new Integer("1").intValue(), new Date() });
		assertEquals(b2 != null && b2.getClass() == B.class, true);

		B b3 = ReflectUtils.clazzInstance("com.zyc.baselibs.commons.C$A$B", new Object[] { "a", "b", new Integer("1").intValue(), new Date() });
		assertEquals(b3 != null && b3.getClass() == B.class, true);

		D d = ReflectUtils.clazzInstance("com.zyc.baselibs.commons.D", new Object[] { "a", "b", new Integer("1").intValue(), new Date() });
		assertEquals(d != null && d.getClass() == D.class, true);
		
		D d2 = ReflectUtils.clazzInstance("com.zyc.baselibs.commons.D", null, new Object[] { "a", "b", new Integer("1").intValue(), new Date() });
		assertEquals(d2 != null && d2.getClass() == D.class, true);
	}

	@Test
	public void findAnnotationTest() {
		Deprecated deprecated = ReflectUtils.findAnnotation("aaa", AA.class, Deprecated.class);
		assertEquals(null != deprecated, true);
	}
}

class AA {
	@Deprecated
	public void aaa() {
		
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