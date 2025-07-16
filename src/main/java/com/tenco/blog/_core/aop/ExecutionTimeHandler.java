package com.tenco.blog._core.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect // AOP <-- 선언
@Component // IoC 대상이 된다. Bean 으로 처리 된다.
@Slf4j
public class ExecutionTimeHandler {

    /**
     * 1.
     * 요청부터 응답까지의 실행시간을 측정하여 로그에 기록하는 Advice로 지정 함
     *
     * 2.
     * JoinPoint 지정( 특정 시점 정의 )
     * @Around : 메서드의 실행 전과 후에 동작하라 명시하라 !
     *
     * 3.
     * PointCut
     * 어떤 메서드가 실행될 때 (Advice) 가 동작할 지 지정 함
     * (표현식 지정 가능 - execution(* com.tenco.blog..*(..)))
     *
     * 4.
     * 어떤 일을 수행하는지 구체적으로 명시
     */

    //@Around("execution(* com.tenco.blog..*(..))")
    @Around("@annotation(org.springframework.web.bind.annotation.GetMapping) || @annotation(org.springframework.web.bind.annotation.PostMapping)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        // ProceedingJoinPoint <-- 중요한 통로 관리자
        // 예시: 학교에 행사를 진행하는 담당자, 행사 진행 전 준비, 끝나고 마무리를 챙김
        // "이제 행사 시작" 신호를 주면 실제 행사(메서드가) 진행 됨
        // 1. 요청 시작 시간을 기록

        long startTime = System.currentTimeMillis();

        // 2. 원래 진행하고자하는 메서드를 진행해라고 신호를 주어야 함
        Object result;
        try {
           result = joinPoint.proceed();
        } catch (Throwable e) {
            log.error("Advice 실행 중 오류 발생 : {}", e.getMessage());
            throw e;
        }

        // 3. 응답 완료 시간 기록 및 실행 시간을 계산
        long endTime = System.currentTimeMillis();
        // 수행시간 계산
        long executionTime = endTime - startTime;

        // 4. 로그에다 실행 시간 출력
        log.info("메서드 : {}, 실행 시간 {}ms", joinPoint.getSignature().getName(), executionTime);

        return result; // 원래 메서드의 결과를 반환

    }

}
