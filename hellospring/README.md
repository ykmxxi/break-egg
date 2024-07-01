# PaymentService 개발

## 요구사항
- 해외직구를 위한 원화 결제 준비 기능 개발
- 주문 번호, 외국 통화 종류, 외국 통화 기준 결제 금액을 전달 받아서 다음의 정보를 더해 `Payment` 생성
    - 적용 환율
    - 원화 환산 금액
    - 원화 환산 금액 유효시간
- `PaymentService.prepare()`로 개발
    - `Payment`오브젝트 return

## 환율 가져오기
- https://open.er-api.com/v6/latest/ {기준통화} 이용
    - ex) https://open.er-api.com/v6/latest/krw
- JSON 포맷으로 리턴되는 값을 분석해서 원화(KRW) 환율 값을 가져온다
- JSON을 Java Object로 변환
    - Jackson 라이브러리의 `ObjectMapper` 사용
