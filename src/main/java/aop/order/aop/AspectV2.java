package aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * spring aop
 */
@Slf4j
@Aspect
public class AspectV2 {

    /**
     * 포인트컷을 직접 지정
     * 다른 애스펙트에서 참고 시 public
     */
    @Pointcut("execution(* aop.order..*(..))")
    private void allOrder() {} // pointcut signature

    /**
     * 포인트컷 메서드 지정
     */
    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature()); // join point signature
        return joinPoint.proceed();
    }
}
