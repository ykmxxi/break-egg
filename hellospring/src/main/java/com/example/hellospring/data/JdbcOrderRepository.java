package com.example.hellospring.data;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.JdbcClient;

import com.example.hellospring.order.Order;
import com.example.hellospring.order.OrderRepository;

import jakarta.annotation.PostConstruct;

public class JdbcOrderRepository implements OrderRepository {

    private final JdbcClient jdbcClient;

    public JdbcOrderRepository(final DataSource dataSource) {
        // 설정 정보에 등록해 생성하는 것 보다 생성장에서 직접 생성을 스프링이 권장
        this.jdbcClient = JdbcClient.create(dataSource);
    }

    @PostConstruct
    void initDb() {
        // 빈 생성시 DB 테이블 생성 작업을 먼저 진행
        jdbcClient.sql("""
                create table orders(id bigint not null, no varchar(255), total numeric(38, 2), primary key (id));
                alter table if exists orders drop constraint if exists UK_43egxxciqr9ncgmxbdx2avi8n;
                alter table if exists orders add constraint UK_43egxxciqr9ncgmxbdx2avi8n unique (no);
                create sequence orders_SEQ start with 1 increment by 50;
                """).update();
    }

    @Override
    public void save(final Order order) {
        Long id = jdbcClient.sql("select next value for orders_SEQ")
                .query(Long.class)
                .single();
        order.setId(id);

        jdbcClient.sql("insert into orders (no,total,id) values (?,?,?)")
                .params(order.getNo(), order.getTotal(), order.getId())
                .update();
        findAll();
    }

    // 저장 조회용 쿼리
    public void findAll() {
        List<Order> orders = jdbcClient.sql("select id, no, total from orders")
                .query((rs, rowNum) -> {
                    Order order = new Order(rs.getString("no"), rs.getBigDecimal("total"));
                    order.setId(rs.getLong("id"));
                    return order;
                }).stream()
                .toList();
        System.out.println("orders = " + orders);
    }

}
