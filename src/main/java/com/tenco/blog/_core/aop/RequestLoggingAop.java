package com.tenco.blog._core.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect // 요청 로깅을 찍어주는 관점
@Component
public class RequestLoggingAop {

    // GET, POST, PUT, DELETE
    @Around("execution(* com.tenco.blog..*RestController.*(..))")
    public Object logRequestInfo (org.aspectj.lang.ProceedingJoinPoint joinPoint) throws Throwable {
        // HttpServletRequest 가져오기
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;

        // 요청 정보 출력
        String requestUri = request != null ? request.getRequestURI() : "Unknown URI";
        String httpMethod = request != null ? request.getMethod() : "Unknown Method";
        String methodName = joinPoint.getSignature().getName();

        log.info("Request - URI: {}, Method: {}, Method Name: {}",
                requestUri, httpMethod, methodName);

        // 원래 메서드 실행
        return joinPoint.proceed();


    }

}
