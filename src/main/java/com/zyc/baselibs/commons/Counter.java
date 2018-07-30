package com.zyc.baselibs.commons;

public class Counter {
	private int count;

	/**
	 * 计数器构造函数：返回一个初始计数为0的计数器对象实例
	 */
	public Counter() {
		this.count = 0;
	}
	
	/**
	 * 计数器构造函数：返回一个初始计数为initCount的计数器对象实例
	 * @param initCount 初始计数
	 */
	public Counter(int initCount) {
		this.count = initCount;
	}
	
	public synchronized int beforeMinus() {
		return --this.count;
	}
	
	public synchronized int afterMinus() {
		return this.count--;
	}
	
	/**
	 * 改变计数值：对原有计数值进行加减操作
	 * @param value 需要加上（正数）或者减去（负数）的值
	 * @return 计算后的值
	 */
	public synchronized int change(int value) {
		this.count += value;
		return this.count;
	}
	
	public synchronized int beforePlus() {
		return ++this.count;
	}
	
	public synchronized int afterPlus() {
		return this.count++;
	}
	
	public int get() {
		return this.count;
	}
}
