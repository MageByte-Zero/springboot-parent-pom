package zero.springboot.study.webcore.util;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <strong>
 * 切面工具类
 * </strong>
 *
 * @version 1.0
 * @Type AspectUtils.java
 * @date 2018年11月6日 下午8:33:59
 */
public class AspectUtils {
    private final static Logger logger = LoggerFactory.getLogger(AspectUtils.class);

    /**
     * 返回切面对应调用方法名
     *
     * @param joinPoint 切面连接点
     */
    public static String getMethodName(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + "()";
        return method;
    }

    /**
     * 返回切面对应调用方法参数
     *
     * @param joinPoint 切面连接点
     * @return String 接口的参数及对应值
     */
    public static String getMethodParams(JoinPoint joinPoint) {
        StringBuffer sb = new StringBuffer();
        try {
            //查找相应方法
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            //查找相应方法参数名称
            String[] parameterNames = methodSignature.getParameterNames();
            if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
                for (int i = 0; i < joinPoint.getArgs().length; i++) {
                    Object param = joinPoint.getArgs()[i];
                    if (param == null || param instanceof ModelMap || param instanceof HttpServletRequest || param instanceof HttpServletResponse || param instanceof MultipartFile) {
                        continue;
                    }
                    if (i > 0) {
                        sb.append(",");
                    }
                    boolean isSimple = BeanUtils.isSimpleValueType(param.getClass());
                    if (isSimple) {
                        sb.append(parameterNames[i]).append(":").append(param);
                    } else {
                        sb.append(parameterNames[i]).append(":").append(JSONObject.toJSONString(param));
                    }
                }
            }
        } catch (Exception e) {
            logger.info("获取切面连接点的方法参数失败", e);
        }
        return sb.toString();
    }

}