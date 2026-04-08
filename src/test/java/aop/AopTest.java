package aop;

import aop.order.OrderRepository;
import aop.order.OrderService;
import aop.order.aop.AspectV1;
import aop.order.aop.AspectV2;
import aop.order.aop.AspectV3;
import aop.order.aop.AspectV5Order;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest
//@Import(AspectV1.class) // 주로 설정 파일을 추가할 때 사용
//@Import(AspectV2.class) // 주로 설정 파일을 추가할 때 사용
//@Import(AspectV3.class) // 주로 설정 파일을 추가할 때 사용
//@Import(AspectV4.class) // 주로 설정 파일을 추가할 때 사용
@Import({AspectV5Order.LogAspect.class, AspectV5Order.TxAspect.class}) // 주로 설정 파일을 추가할 때 사용
public class AopTest {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;


    @Test
    void aopInfo() {
        log.info("isAopProxy, orderService={}", AopUtils.isAopProxy(orderService));
        log.info("isAopProxy, orderRepository={}", AopUtils.isAopProxy(orderRepository));
    }

    @Test
    void success() {
        orderService.orderItem("itemA");
    }

    @Test
    void exception() {
        Assertions.assertThatThrownBy(() -> orderService.orderItem("ex")).isInstanceOf(IllegalStateException.class);
    }
}

