package com.example.order.order;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.example.order.ApiTest;
import com.example.order.product.ProductSteps;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class OrderApiTest extends ApiTest {

    @Test
    void 상품주문_API() {
        ProductSteps.상품등록요청(ProductSteps.상품등록요청_생성());
        CreateOrderRequest request = OrderSteps.상품주문요청_생성();

        ExtractableResponse<Response> response = OrderSteps.상품주문요청(request);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

}
