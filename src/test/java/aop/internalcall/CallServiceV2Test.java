package aop.internalcall;

import aop.internalcall.aop.CallLogAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;


@Slf4j
@Import(CallLogAspect.class)
@SpringBootTest
class CallServiceV2Test {

    @Autowired
    CallServiceV2 callService;

    /**
     * external() -> proxy 호출. aop 호출.
     * internal() -> this.internal() -> 실제 객체 호출.
     * AOP가 타겟을 호출하는게 아닌, 타겟이 스스로의 메서드를 호출함. AOP 적용 안됨. (트랜잭션에서도 마찬가지)
     */
    @Test
    void external() {
        log.info("target={}", callService.getClass());
        callService.external();
    }

    @Test
    void internal() {
        callService.internal();

    }
}