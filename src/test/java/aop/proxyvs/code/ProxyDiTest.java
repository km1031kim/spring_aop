package aop.proxyvs.code;

import aop.member.MemberService;
import aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * 스프링은 기본적으로 CGLIB 구체 클래스 기반 프록시 사용
 */
@Slf4j
@Import(ProxyDIAspect.class)
//@SpringBootTest(properties = {"spring.aop.proxy-target-class=false"}) // JDK 동적 프록시
@SpringBootTest // JDK 동적 프록시
public class ProxyDiTest {

    @Autowired
    MemberService memberService;

        // JDK 동적 프록시는 구현 클래스로 타입 캐스팅 안됨
    @Autowired
    MemberServiceImpl memberServiceImpl;

    @Test
    void go() {
        log.info("memberService class={}", memberService.getClass());
        log.info("memberServiceImpl class={}", memberServiceImpl.getClass());
        memberServiceImpl.hello("hello");
    }

    /**
     * CGLIB 구체 클래스 기반 프록시 문제점
     * 대상 클래스에 기본 생성자 필수
     * 생성자 2번 호출 문제
     * -> 타겟 클래스 생성 시, 프록시 상속 시 부모 생성자로 호출 -> 생성자 2번 호출..
     * -> 스프링 4.0부터 objenesis 라는 특별한 라이브러리르 사용해서 기본 생성자 없이 객체 생성가능.
     * final 키워드 클래스, 메서드 사용 불가
     * -> 어차피 AOP 대상에는 이걸 잘 안쓴다.
     *
     * 결론 :  CGLIB이 디폴트다. 클라이언트 입장에서는 모르고 쓰면 된다.
     */
}
