package com.example.hellospring.order;

import java.math.BigDecimal;

public class Order {

    private Long id;

    private String no; // 의미있는 주문 번호

    private BigDecimal total;

    protected Order() {}

    public Order(final String no, final BigDecimal total) {
        this.no = no;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public String getNo() {
        return no;
    }

    public BigDecimal getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", no='" + no + '\'' +
                ", total=" + total +
                '}';
    }

}
