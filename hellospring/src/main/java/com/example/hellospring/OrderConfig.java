package com.example.hellospring;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.hellospring.data.JdbcOrderRepository;
import com.example.hellospring.order.OrderRepository;
import com.example.hellospring.order.OrderService;
import com.example.hellospring.order.OrderServiceImpl;
import com.example.hellospring.order.OrderServiceTxProxy;

@Configuration
@Import(DataConfig.class)
public class OrderConfig {

    @Bean
    public OrderRepository orderRepository(final DataSource dataSource) {
        return new JdbcOrderRepository(dataSource);
    }

    @Bean
    public OrderService orderService(
            final OrderRepository orderRepository,
            final PlatformTransactionManager transactionManager
    ) {
        return new OrderServiceTxProxy(
                new OrderServiceImpl(orderRepository),
                transactionManager);
    }

}
