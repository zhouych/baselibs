package com.zyc.baselibs.commons;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CounterTest {

	@Test
	public void test() {
		Counter c = new Counter();
		assertEquals(c.get(), 0);
		assertEquals(c.beforePlus(), 1);
		assertEquals(c.get(), 1);
		assertEquals(c.afterPlus(), 1);
		assertEquals(c.get(), 2);
		assertEquals(c.beforeMinus(), 1);
		assertEquals(c.get(), 1);
		assertEquals(c.afterMinus(), 1);
		assertEquals(c.get(), 0);
		assertEquals(c.beforePlus(), 1);
		assertEquals(c.change(10), 11);
	}

	@Test
	public void testSync() {
		System.out.println("Running testSync() ...");
		
		final TestCounter tc = new TestCounter();
		
		new Thread(new Runnable() {
			public void run() {
				System.out.println("Thread beforePlus ... ");
				int value = tc.beforePlus();
				assertEquals(value, 1);
			}
		}).start();
		
		new Thread(new Runnable() {
			public void run() {
				System.out.println("Thread afterPlus ... ");
				int value = tc.afterPlus();
				assertEquals(value, 1);
			}
		}).start();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	class TestCounter extends Counter {
		@Override
		public synchronized int beforePlus() {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Running beforePlus() ...");
			return super.beforePlus();
		}
		
		@Override
		public synchronized int afterPlus() {
			System.out.println("Running afterPlus() ...");
			return super.afterPlus();
		}
	}
}
