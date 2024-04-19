package com.example.order.payment;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.example.order.ApiTest;
import com.example.order.order.OrderSteps;
import com.example.order.payment.service.PaymentRequest;
import com.example.order.product.ProductSteps;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class PaymentApiTest extends ApiTest {

    @Test
    void 상품주문결제() {
        ProductSteps.상품등록요청(ProductSteps.상품등록요청_생성());
        OrderSteps.상품주문요청(OrderSteps.상품주문요청_생성());

        PaymentRequest request = PaymentSteps.주문결제요청_생성();

        ExtractableResponse<Response> response = PaymentSteps.주문결제요청(request);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

}
