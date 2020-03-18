package com.zero.api.core;


/**
 * <strong>
 * 自定义异常类
 * </strong>
 * @type BaseException.java
 * @author libai
 * @date 2018年10月13日 下午4:35:40
 * @version 1.0
 */
public class BaseException extends RuntimeException {
	
	private static final long serialVersionUID = 6528764782020895991L;
	/**状态码 */
	protected String errorCode;
	/**状态信息 */
	protected String errorMessage;

	public BaseException() {
	}

	public BaseException(Throwable ex) {
		super(ex);
	}

	public BaseException(String message) {
		super(message);
		this.errorMessage = message;
	}

	public BaseException(String message, Throwable ex) {
		super(message, ex);
		this.errorMessage = message;
	}

	public BaseException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
		this.errorMessage = message;
	}

	public BaseException(ResultCodeEnum code) {
		super(code.getMessage());
		this.errorCode = code.getCode();
		this.errorMessage = code.getMessage();
	}
	
	public BaseException(String errorCode, String message, Throwable ex) {
        super(message, ex);
        this.errorCode = errorCode;
        this.errorMessage = message;
    }

	public BaseException(ResultCodeEnum code, Throwable ex) {
		super(code.getMessage(), ex);
		this.errorCode = code.getCode();
		this.errorMessage = code.getMessage();
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	// @Override //不拷贝栈信息
	// public Throwable fillInStackTrace() {
	// return null;
	// }
}
