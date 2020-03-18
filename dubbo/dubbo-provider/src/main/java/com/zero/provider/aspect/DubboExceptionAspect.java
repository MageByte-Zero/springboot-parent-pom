package com.zero.provider.aspect;

import com.alibaba.fastjson.JSONObject;
import com.zero.api.core.BaseException;
import com.zero.api.core.InvokerLog;
import com.zero.api.core.ResultCodeEnum;
import com.zero.provider.utils.AspectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**<strong>
 * dubbo的接口调用切面类，主要用于记录接口调用信息及失败时详情
 * </strong>
 * @type DubboExceptionAspect.java
 * @date 2018年10月24日 上午11:55:16
 * @version 1.0
 */
@Aspect
@Component
public class DubboExceptionAspect {

    private final static Logger logger = LoggerFactory.getLogger(DubboExceptionAspect.class);

    /**
     * 定义切入点
     */
    @Pointcut("@within(org.apache.dubbo.config.annotation.Service)")
    public void dubboExceptionPointcut() {
    }

    /**
     * 切面的around方法，记录接口的调用信息及异常信息
     * @param joinPoint 切面连接点
     * @throws Throwable
     */
    @Around(value = "dubboExceptionPointcut()")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        long startTime = System.currentTimeMillis();
        try {
            result = joinPoint.proceed();
        } catch (BaseException e) {
        	InvokerLog invoker = AspectUtils.formatInvokerResultMsg(joinPoint, startTime, result);
            invoker.setCode(e.getErrorCode());
            String errorMsg = (e.getErrorMessage() == null) ? ((e.getMessage() == null) ? "" : e.getMessage()) : e.getErrorMessage();
            invoker.setMessage(errorMsg);
            logger.warn("RPC接口调用失败，异常类型为BaseException, 失败详情: {}", JSONObject.toJSONString(invoker), e);
            throw e;
        } catch (Exception e) {
        	InvokerLog invoker = AspectUtils.formatInvokerResultMsg(joinPoint, startTime, result);
            invoker.setCode(ResultCodeEnum.ERROR_CODE_10100.getCode());
            String errorMsg = (e.getMessage() == null) ? ResultCodeEnum.ERROR_CODE_10100.getMessage() : e.getMessage();
            invoker.setMessage(errorMsg);
            logger.warn("RPC接口调用失败，异常类型为{}, 失败详情: {}", e.getClass().getSimpleName(), JSONObject.toJSONString(invoker), e);
            throw e;
        }
        InvokerLog invoker = AspectUtils.formatInvokerResultMsg(joinPoint, startTime, result);
        logger.info("RPC接口调用成功，接口调用详情: {}", JSONObject.toJSONString(invoker));
        return result;
    }

}
