package aop.internalcall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 내부 호출 문제 해결3 : 구조 변경
 * 영한센세와 스프링이 권장하는 방법.
 * 애초에 트랜잭셔널이 걸린 메서드를 호출하면 프록시 객체가 호출되고,
 * 그 내부에서 this를 호출하니까 그건 괜찮다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CallServiceV3 {

    private final InternalService internalService;

    public void external() {
        log.info("call external");
        internalService.internal();
    }
}
