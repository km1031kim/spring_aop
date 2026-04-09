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
@SpringBootTest(properties = {"spring.aop.proxy-target-class=false"}) // JDK 동적 프록시
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
}
