package com.meli.magneto.controller;

public class ResponseError {

	private String code;
	private String message;
	public ResponseError() {
		super();
		// TODO Auto-generated constructor stub
	}
		
	public ResponseError(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}


	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
