package com.bmw.entity.response;

public class RestResponse<T> {

	private Integer code;
	private String message;
	private T data;
	
	public RestResponse(){
		this.code = 0;
		this.message = "success";
	}
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
}
