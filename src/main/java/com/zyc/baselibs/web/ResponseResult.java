package com.zyc.baselibs.web;

public class ResponseResult {
	
	/**
	 * 状态码: 0-正常/成功/无异常/没有问题等等
	 */
	private String status = "0";
	
	/**
	 * 消息：包含正常/成功/无异常下的提示消息，也包括错误提示消息/异常消息等等
	 */
	private String message;
	
	/**
	 * 数据：例如实体对象/数据集等等
	 */
	private Object data;
	
	public ResponseResult() {
		
	}
	
	public ResponseResult(String status, String message, Object data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
