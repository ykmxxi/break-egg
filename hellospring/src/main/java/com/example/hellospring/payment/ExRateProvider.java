package com.example.hellospring.payment;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Separate Interface 패턴: 의존성 역전 원칙(DIP)을 적용
 * 인터페이스는 구현 클래스가 있는 모듈이 아니라, 사용하는 클라이언트의 모듈로 이동
 * 인터페이스의 소유권을 클라이언트가 존재하는 곳으로 이동 -> 인터페이스 소유권의 역전
 */
public interface ExRateProvider {

    BigDecimal getExchangeRate(String currency) throws IOException;

}
