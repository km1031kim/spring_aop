package aop.pointcut;

import aop.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * application.properties 에서 적용하거나
 * @SpringBootTest에서 property 설정하거나
 */
@Slf4j
@Import(ThisTargetTest.ThisTargetAspect.class)
@SpringBootTest(properties = "spring.aop.proxy-target-class=false") // jdk 동적  프록시
//@SpringBootTest(properties = "spring.aop.proxy-target-class=true") // cglib
public class ThisTargetTest {



    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService proxy={}", memberService.getClass());
        memberService.hello("helloA");
    }

    @Slf4j
    @Aspect
    static class ThisTargetAspect {
        @Around("this(aop.member.MemberService)")
        public Object doThisInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        @Around("target(aop.member.MemberService)")
        public Object doTargetInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-interface] {}", joinPoint.getSignature());

            return joinPoint.proceed();
        }


        /**
         * JDK 동적 프록시는 인터페이스 기반임.
         * MemberService 인터페이스만 알고 있음. 구체 클래스는 모름. 원본 모름.
         * 따라서 JDK 동적 프록시 사용 시 this로 구현 클래스를 대상으로 할 수 없음.
         *
         * CGLIB는 구체 클래스를 대상으로 상속하여 프록시 생성.
         * 프록시 객체가 원본을 알고 있음.
         */
        @Around("this(aop.member.MemberServiceImpl)")
        public Object doThis(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-impl] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        @Around("target(aop.member.MemberServiceImpl)")
        public Object doTarget(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-impl] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
}
