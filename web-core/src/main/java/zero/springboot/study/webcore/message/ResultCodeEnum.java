package zero.springboot.study.webcore.message;

/**
 * <strong>
 * 错误码枚举类：00-09=预留，10-49=系统类错误，50-89=业务类错误，90-99=预留数字
 * </strong>
 * @Type ResultCodeEnum
 * @version 1.0
 */
public enum ResultCodeEnum {
    SUCCESS("200", "OK"),
    FAIL("10001", "系统异常"),
    ERROR_CODE_10100("10100", "rpc调用异常"),
    ERROR_CODE_10101("10101", "rpc调用超时异常"),
    
    ERROR_CODE_10200("10200", "Web调用异常"),
    ERROR_CODE_10400("10400", "权限不足"),
    ERROR_CODE_10500("10500", "认证异常"),
    ERROR_CODE_10600("10600", "检验异常"),

    /**
     * jdbc
     */
    ERROR_CODE_10300("10300", "SQL调用异常"),
    ERROR_CODE_10301("10301", "数据库连接失败"),
    ERROR_CODE_10302("10302", "查询数据库表信息失败"),
    ERROR_CODE_10303("10303", "查询数据库表定义失败"),



    /**
     * api加密
     */
    ERROR_CODE_10700("10700", "签名验证失败!"),
    ERROR_CODE_10701("10701", "签名验证失败!请求参数为空"),
    ERROR_CODE_10702("10702", "签名验证失败!请求参数缺少appId值"),
    ERROR_CODE_10703("10703", "签名验证失败!请求参数缺少sign值"),
    ERROR_CODE_10704("10704", "签名验证失败!请求参数缺少timestamp值"),
    ERROR_CODE_10705("10705", "签名验证失败!请求时间超时"),
    ERROR_CODE_10706("10706", "签名验证失败!请求业务参数为空"),
    ERROR_CODE_10707("10707", "签名验证失败!secret值为空"),
    ERROR_CODE_10708("10708", "签名验证失败!使用RSA私钥解密失败"),
    ERROR_CODE_10709("10709", "签名验证失败!请求参数缺少encryptParam值"),
    ERROR_CODE_10710("10710", "签名验证失败!使用AES秘钥解密失败"),

    ERROR_CODE_10711("10711", "签名失败！MD5加密失败"),
    ERROR_CODE_10712("10712", "签名失败！SHA256加密失败"),
    ERROR_CODE_10713("10713", "签名失败！RSA初始化秘钥失败"),
    ERROR_CODE_10714("10714", "签名失败！RSA获取私钥失败"),
    ERROR_CODE_10715("10715", "签名失败！RSA获取公钥失败"),
    ERROR_CODE_10716("10716", "签名失败！RSA公钥加密失败"),
    ERROR_CODE_10717("10717", "签名失败！RSA私钥加密失败"),
    ERROR_CODE_10718("10718", "签名失败！RSA私钥解密失败"),
    ERROR_CODE_10719("10719", "签名失败！RSA公钥解密失败"),
    ERROR_CODE_10720("10720", "签名失败！AES获取秘钥失败"),
    ERROR_CODE_10721("10721", "签名失败！AES加密失败"),
    ERROR_CODE_10722("10722", "签名失败！AES解密失败"),

    ERROR_CODE_10800("10800", "http请求失败"),
    ;

    private final String code;
    private final String message;
    ResultCodeEnum(String code, String message){
        this.code = code;
        this.message = message;
    }

    public static ResultCodeEnum getResultCode(String code){
        ResultCodeEnum[] codes = ResultCodeEnum.values();
        for(ResultCodeEnum ce : codes){
            if(ce.code.equals(code)){
                return ce;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
