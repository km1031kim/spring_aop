package aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {
    /**
     * 포인트컷을 직접 지정
     * 다른 애스펙트에서 참고 시 public
     */
    @Pointcut("execution(* aop.order..*(..))")
    public void allOrder() {
    } // pointcut signature

    // 클래스 이름 패턴 *Service
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService() {
    }

    @Pointcut("allOrder() && allService()")
    public void orderAndService(){}

}
