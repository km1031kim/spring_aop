package aop.proxyvs;

import aop.member.MemberService;
import aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

@Slf4j
public class ProxyCastingTest {

    @Test
    void jdkProxy() {
        /**
         * 인터페이스 기반, 타겟을 내부에 가지고 있음. -> 타입 변환 안됨
         */
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(false); // JDK 동적 프록시

        // 프록시를 인터페이스로 캐스팅 성공 -> JDK 동적 프록시는 인터페이스 기반 생성
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

        // 에러남 -> JDK Proxy는 MemberService 인터페이스를 구현한거임. MemberServiceImpl은 모름.
        Assertions.assertThrows(ClassCastException.class, () -> {
            MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;});

    }


    @Test
    void cglib() {
        /**
         * 구체 클래스를 상속. -> 타입 변환 가능
         * */
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true); // cglib proxy

        // 프록시를 인터페이스로 캐스팅 성공 -> 구현체 기반
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

        MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;

    }
}
