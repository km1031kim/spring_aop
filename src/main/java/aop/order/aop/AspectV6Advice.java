package aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class AspectV6Advice {

    @Around("aop.order.aop.Pointcuts.allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature()); // join point signature
        return joinPoint.proceed();
    }

    /**
     * 메서드 실행 전후 작업 실행. 가장 막강한 어드바이스다.
     * 조인포인트의 proceed() 메서드로 실행 여부를 결정할 수 있다.
     * proceed() 아규먼트로 다른 인자를 넘겨 줄 수 있음.
     * 반환 값 변환, 예외 변환이 가능하다.
     * try catch finally 구문을 사용할 수 있다.
     * proceed()를 여러번 실행할 수도 있다 (재시도)
     * 제약 : 첫번쨰 파라미터로 ProceedJoinPoint를 사용해야 한다.
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("aop.order.aop.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            //@Before
            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();

            //@AfterReturning
            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            //@AfterThrowing
            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            // @After
            log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }

    /**
     * proceed()를 호출하지 않는다.
     * 왜 이렇게 분리했을까?
     * @Around는 조인포인트의 proceed를 꼭 호출해야 한다.
     * 개발자가 명시하지 않는다면 타겟을 호출하지 않기에 심각한 오류가 발생할 수 있다.
     * 실수를 방지하기 위해 분리.. 어드바이스로 의도를 명확히 할 수 있다.
     * @param joinPoint
     */
    @Before("aop.order.aop.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) {
        /**
         * @Before는 joinpoint 를 알아서 실행해준다.
         *
         */
        log.info("[before] {}", joinPoint.getSignature());
    }

    /**
     * 타겟 호출 후에 실행되는 어드바이스
     * 반환 타입이 맞아야 두번째 인자로 result가 들어온다.
     * @param joinPoint
     * @param result
     */
    @AfterReturning(value = "aop.order.aop.Pointcuts.orderAndService()", returning = "result")
    public void deReturn(JoinPoint joinPoint, Object result) {
        log.info("[return] {} return={}", joinPoint.getSignature(), result);
    }

    /**
     * orderRepository.save() 의 결과인 ok 를 String 타입의 result로 받을 수 있다.
     * 반환 타입이 맞지 않으면 해당 메서드가 호출되지 않는다. -> Object 쓰자.
     * 리턴하는 객체를 변환할 수 없다.
     * @param joinPoint
     * @param result
     */
    @AfterReturning(value = "aop.order.aop.Pointcuts.allOrder()", returning = "result")
    public void deReturn2(JoinPoint joinPoint, String result) {
        log.info("[return2] {} return={}", joinPoint.getSignature(), result);
    }

    /**
     * 메서드 실행이 예외를 던져서 종료될 때 실행.
     * @AfterReturning과 마찬가지로 부모 타입의 예외로 받아야 해당 어드바이스가 실행된다.
     * @param joinPoint
     * @param ex
     */
    @AfterThrowing(value = "aop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex) {
        log.info("[ex] {} message={}", joinPoint.getSignature(), ex);
    }

    /**
     * 메서드 실행이 종료되면 실행된다.
     * 일반적으로 리소스를 해제하는 데 사용된다.
     * @param joinPoint
     */
    @After(value = "aop.order.aop.Pointcuts.orderAndService()")
    public void doAFter(JoinPoint joinPoint) {
        log.info("[after] {}", joinPoint.getSignature());
    }
}
