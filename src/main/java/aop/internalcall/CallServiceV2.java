package aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV2 {

    /**
     * ApplicationContext는 너무 많은 기능을 제공.
     * ObjectProvider를 사용하여 스프링 빈 생성 시점이 아닌 실제 객체 생성 시점으로 지연 로딩
     */
//    private final ApplicationContext applicationContext;
    private final ObjectProvider<CallServiceV2> callServiceProvider;

    public CallServiceV2(ObjectProvider<CallServiceV2> callServiceProvider) {
        this.callServiceProvider = callServiceProvider;

    }

    public void external() {
        log.info("call external");
            CallServiceV2 callServiceV2 = callServiceProvider.getObject();
//        CallServiceV2 callServiceV2 = applicationContext.getBean(CallServiceV2.class);// 내부 메서드 호출(this.internal())
        callServiceV2.internal();
    }

    public void internal() {
        log.info("call internal");
    }
}
