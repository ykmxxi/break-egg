package com.example.order.product;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.example.order.ApiTest;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

class ProductApiTest extends ApiTest {

    @Test
    void 상품등록_API() {
        AddProductRequest request = ProductSteps.상품등록요청_생성();

        // API 요청
        ExtractableResponse<Response> response = ProductSteps.상품등록요청(request);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void 상품조회_API() {
        AddProductRequest request = ProductSteps.상품등록요청_생성();

        ExtractableResponse<Response> productAddResponse = ProductSteps.상품등록요청(request);

        Long productId = 1L;

        ExtractableResponse<Response> response = ProductSteps.상품조회요청(productId);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getString("name")).isEqualTo("상품명");
    }

}
