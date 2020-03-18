package com.zero.provider.utils;

import com.alibaba.fastjson.JSONObject;
import com.zero.api.core.InvokerLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.ui.ModelMap;

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
     * @return 接口的参数及对应值
     */
    public static String getMethodParams(JoinPoint joinPoint) {
        StringBuffer sb = new StringBuffer();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature(); //查找相应方法
        String[] parameterNames = methodSignature.getParameterNames(); //查找相应方法参数名称
        if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
            for (int i = 0; i < joinPoint.getArgs().length; i++) {
                Object param = joinPoint.getArgs()[i];
                if (param == null || param instanceof ModelMap) {
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
        return sb.toString();
    }

    /**
     * 格式化接口调用结果的
     *
     * @param joinPoint 切面连接点
     * @param startTime 接口调用的开始时间
     * @param result    方法返回的result
     * @return InvokerResultMsg
     */
    public static InvokerLog formatInvokerResultMsg(JoinPoint joinPoint, Long startTime, Object result) {
        InvokerLog invoker = new InvokerLog();
        long endTime = System.currentTimeMillis();
        try {
            invoker.setInterfaceClass(joinPoint.getSignature().getDeclaringTypeName());
            invoker.setMethodName(joinPoint.getSignature().getName());
            invoker.setParameters(AspectUtils.getMethodParams(joinPoint));
            long executeTime = endTime - startTime;
            invoker.setStartTimeStr(DateUtils.transferLongToString(startTime, DateUtils.YYYY_MM_DD_HH_MM_SS_S));
            invoker.setEndTimeStr(DateUtils.transferLongToString(endTime, DateUtils.YYYY_MM_DD_HH_MM_SS_S));
            invoker.setExecuteTime(executeTime);
            invoker.setData(result);
        } catch (Exception e) {
            logger.warn("封装rpc接口调用结果失败，message={}", e.getMessage(), e);
        }
        return invoker;
    }

}
