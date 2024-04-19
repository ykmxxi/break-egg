package com.example.order.product;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.example.order.ApiTest;
import com.example.order.product.adapter.ProductRepository;
import com.example.order.product.service.AddProductRequest;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

class ProductApiTest extends ApiTest {

    @Autowired
    private ProductRepository productRepository;

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

    @Test
    void 상품수정_API() {
        ProductSteps.상품등록요청(ProductSteps.상품등록요청_생성());
        Long productId = 1L;

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(ProductSteps.상품수정요청_생성())
                .when()
                .patch("/products/{productId}", productId)
                .then()
                .log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

        assertThat(productRepository.findById(1L).get().getName()).isEqualTo("상품수정");
    }

}
