package aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
//@Component
public class CallServiceV1 {

    private CallServiceV1 callService;

    /**
     * 스스로를 감싼 프록시 객체 호출하기
     * @param callService
     */
    @Autowired
    public void setCallServiceV1(CallServiceV1 callService) {
        log.info("callServiceV1 setter={}", callService.getClass());
        this.callService = callService;
    }


    public void external() {
        log.info("call external");
        callService.internal(); // 내부 메서드 호출(this.internal())
    }

    public void internal() {
        log.info("call internal");
    }
}
