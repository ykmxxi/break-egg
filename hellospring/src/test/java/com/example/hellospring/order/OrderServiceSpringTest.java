package com.example.hellospring.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.hellospring.OrderConfig;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = OrderConfig.class)
class OrderServiceSpringTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    DataSource dataSource;

    @Test
    void createOrder() {
        // 인스턴스와 메서드 이름으로 어떤 타입이 올지 명확히 보인다면 var 사용해도 좋음
        var order = orderService.createOrder("0100", BigDecimal.ONE);

        assertThat(order.getId()).isGreaterThan(0L);
    }

    @Test
    void createOrders() {
        List<OrderRequest> orderRequests = List.of(
                new OrderRequest("0200", BigDecimal.ONE),
                new OrderRequest("0201", BigDecimal.TWO)
        );

        var orders = orderService.createOrders(orderRequests);

        assertThat(orders).hasSize(2);
        orders.forEach(order -> {
            assertThat(order.getId()).isGreaterThan(0L);
        });
    }

    @Test
    @DisplayName("트랜잭션 내의 작업이 하나라도 실패하면 전체 rollback")
    void duplicatedOrderNo() {
        List<OrderRequest> orderRequests = List.of(
                new OrderRequest("0300", BigDecimal.ONE),
                new OrderRequest("0300", BigDecimal.TWO)
        );

        assertThatThrownBy(() -> orderService.createOrders(orderRequests))
                .isInstanceOf(DataIntegrityViolationException.class);

        JdbcClient client = JdbcClient.create(dataSource);
        Long count = client.sql("select count(*) from orders where no = '0300'")
                .query(Long.class)
                .single();
        assertThat(count).isEqualTo(0L);
    }

}
