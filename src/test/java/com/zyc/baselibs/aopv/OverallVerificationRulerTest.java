package com.zyc.baselibs.aopv;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.zyc.baselibs.annotation.FieldRule;

public class OverallVerificationRulerTest {

	@Test
	public void executeTest() {
		final OverallVerificationRuler ruler = new OverallVerificationRuler();
		final A a = new A();
		a.setName("n");
		a.setCode("c");
		a.setList1(new ArrayList<A>());
		a.getList1().add(new A());
		
		new Thread(new Runnable() {
			
			public void run() {
				try {
					System.out.println("1111111111111");
					ruler.execute(a);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

		new Thread(new Runnable() {
			
			public void run() {
				try {
					System.out.println("2222222222222222");
					ruler.execute(a);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		System.out.println("end.");
	}
}

@Verifiable
class A {
	@FieldRule
	private String code;
	private String name;
	private List<A> list1;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<A> getList1() {
		return list1;
	}
	public void setList1(List<A> list1) {
		this.list1 = list1;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
