package zero.springboot.study.webcore.message;

import java.io.Serializable;

/**
 * @version 1.0
 * @date 2018年9月26日 下午8:29:49
 */
public class ResultDTO<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    protected String code = "200";

    /**
     * 状态信息
     */
    protected String message = "OK";

    /**
     * 请求url,用于异常时记录
     */
    protected String url;

    /**
     * 返回的结果泛型
     */
    protected T data;

    public ResultDTO() {
        super();
    }

    public ResultDTO(T data) {
        super();
        this.data = data;
    }

    /**
     * 调用处理失败时构造器
     *
     * @param failResultCodeEnum 调用处理失败返回的错误码
     */
    public ResultDTO(ResultCodeEnum failResultCodeEnum) {
        super();
        this.code = failResultCodeEnum.getCode();
        this.message = failResultCodeEnum.getMessage();
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
