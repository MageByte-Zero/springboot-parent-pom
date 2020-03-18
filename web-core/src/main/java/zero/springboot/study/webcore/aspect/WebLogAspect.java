package zero.springboot.study.webcore.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import zero.springboot.study.webcore.util.AspectUtils;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
@Aspect
@Order(5)
@Component
@Slf4j
public class WebLogAspect {

    /**
     * 匹配RestController或者Controller注解的类的方法
     */
    @Pointcut("@within(org.springframework.web.bind.annotation.RestController) " +
            "|| @within(org.springframework.stereotype.Controller)")
    public void webLog() {
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        long startTime = System.currentTimeMillis();

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        log.info("URL : " + request.getRequestURL().toString());
        log.info("HTTP_METHOD : " + request.getMethod());
        log.info("IP : " + request.getRemoteAddr());
        log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("ARGS : " + AspectUtils.getMethodParams(joinPoint));

        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            throw throwable;
        }
        log.info("SPEND TIME : " + (System.currentTimeMillis() - startTime));
        return result;

    }

}
