/**
 * File created at 2018年10月25日
 */
package com.zero.api.core;

import java.io.Serializable;

/**
 * <strong>
 * 接口调用信息类，用于接口调用日志记录，比如web，rpc
 * </strong>
 *
 * @version 1.0
 * @type InvokerResultMsg.java
 * @date 2018年10月25日 下午2:52:06
 */
public class InvokerLog<T> implements Serializable {

    /**   */
    private static final long serialVersionUID = 1L;

    /**
     * 访问host
     */
    private String host;

    /**
     * 接口类名称
     */
    private String interfaceClass;

    /**
     * 接口方法名称
     */
    private String methodName;

    /**
     * 接口方法参数
     */
    private String parameters;

    /**
     * 当前用户
     */
    private String userName;

    /**
     * 当前用户id
     */
    private Long userId;

    /**
     * 调用方法url，主要用于web
     */
    private String url;

    /**
     * 接口开始时间
     */
    private String startTimeStr;

    /**
     * 接口结束时间
     */
    private String endTimeStr;

    /**
     * 接口总耗时,单位为毫秒/ms
     */
    private Long executeTime;

    /**
     * 接口返回状态码
     */
    private String code = "200";

    /**
     * 接口返回状态信息，接口正常返回时message为null
     */
    private String message;

    /**
     * 接口正常返回时的结果
     */
    private T data;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(String interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    public Long getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Long executeTime) {
        this.executeTime = executeTime;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
