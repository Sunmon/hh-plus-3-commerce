# hh-plus-3-commerce

e-커머스 상품 주문 서비스

## 기능 목록

- [ ] 잔액 조회 API
- [ ] 잔액 충전 API
    - [ ] 잔액 충전 내역 저장
    - [ ] 유저 ID, 충전 금액 입력 -> ID, 충전 후 금액 리턴
- [ ] 상품 조회 API
    - [ ] 상품 ID로 조회 -> ID, 이름, 가격, 잔여수량 리턴
- [ ] 상품 주문 API
    - [ ] 상품 주문 내역 저장
    - [ ] 유저 ID로 조회 -> ID, 상품 ID, 주문 수량 리턴
- [ ] 결제
    - [ ] 결제 내역 조회
    - [ ] 유저 ID, 결제금액 입력 -> 유저 ID, 잔액 리턴
- [ ] 인기 판매 상품 조회 API
    - [ ] 판매량 순으로 상품 조회

고도화

- [ ] 다중 인스턴스 지원 (동시성 고려)
- [ ] 멀티쓰레드 지원 (동시성 고려)
- [ ] 재고관리 (문제가 없도록?)
- [ ] 단위 테스트 작성

## 마일스톤

4주차 (10/13 ~ 10/18) - 4.5MD <기본 기능 구현>

- 잔액 조회 기능 구현 및 테스트코드 작성: 0.5MD
    - 잔액 조회 기능
    - 테스트 코드 (유닛테스트)
- 잔액 충전 기능 구현 및 테스트코드 작성: 0.5MD
    - 잔액 충전 기능
    - 잔액 충전 내역 저장
    - 테스트 코드 (유닛테스트)
- 상품 조회 기능 구현 및 테스트코드 작성: 0.5MD
    - 상품 조회 기능
    - 테스트코드 (유닛테스트)
- 상품 주문 기능 구현 및 테스트코드 작성: 1MD
    - 상품 주문 기능
    - 주문 내역 저장
    - 테스트코드 (유닛테스트)
- 결제 주문 기능 구현 및 텥스트코드 작성: 1MD
    - 결제 기능
    - 결제 내역 저장
    - 테스트코드 (유닛테스트)
- 인기 상품 조회 API 구현 및 테스트코드 작성: 1MD
    - 인기 상품 조회 기능
    - 테스트코드 (유닛테스트)

5주차 (10/20~ 10/26) - 4MD <고도화>

- 동시성 고려, 보완: 2MD
    - 다중 인스턴스 지원
    - 멀티쓰레드 지원
- 리팩토링: 1MD
    - 코드 리팩토링
    - 이벤트 기반으로 변경 가능성 고려?
    - 비동기로 처리 가능성 고려?
- 통합테스트 작성: 1MD

## 상품 주문 API 시퀀스 다이어그램

```mermaid
sequenceDiagram
    actor User
    participant Order_Server
    participant Order_DB
    participant Pay_Server
    participant Pay_DB
    User->>+Order_Server: 상품 주문 (유저id, 상품id, 수량)
    Order_Server->>+Order_DB: 상품 정보 조회
    Order_DB-->>-Order_Server: 상품 정보 리턴(상품 id, 가격, 재고)
    opt 존재하지 않는 상품
    Order_Server-->>User: 오류 응답 반환 (존재하지 않는 상품)
    end
    opt 재고 부족
    Order_Server->>User: 오류 응답 반환 (재고부족)
    end
    Order_Server-->> Order_Server: 결제 금액 계산
    Order_Server->>+Pay_Server: 결제 요청(유저id, 결제금액)
    Pay_Server->>+Pay_DB: 사용자 정보 조회(유저id)
    Pay_DB-->>-Pay_Server: 사용자 정보 리턴(유저id, 계좌잔액)
    Pay_Server-->>Pay_Server: 결제후 잔액 계산
    opt 잔액부족
    Pay_Server-->>Order_Server: 오류 응답 반환(잔액부족)
    Order_Server-->>User: 오류 응답 반환(잔액부족)
    end
    Pay_Server->>+Pay_DB: 사용자 정보 업데이트(유저id, 계좌잔액)
    Pay_DB-->>-Pay_Server: 업데이트 결과 반환(유저id, 계좌잔액)
    opt  업데이트 실패
    Pay_Server->>Order_Server: 오류 응답 반환(계좌 업데이트 실패)
    Order_Server->>User: 오류 응답 반환(결제 실패)
    end
    Pay_Server->>+Pay_DB: 사용자 결제 히스토리 추가(유저id, 결제금액)
    Pay_DB-->>-Pay_Server: 사용자 결제 히스토리 추가 결과 리턴
    opt 히스토리 추가 실패
    Pay_Server->>Order_Server: 오류 응답 반환 (히스토리 추가 실패)
    Order_Server->>User: 오류 응답 반환(결제 실패)
    end
    Pay_Server-->>-Order_Server: 사용자 정보 리턴(유저 id, 잔액)
    Order_Server->>Order_DB: 상품 업데이트(상품id, 상품재고)
    Order_DB-->>Order_Server: 상품 업데이트 결과 반환
    opt 상품 업데이트 실패
    Order_Server-->User: 오류 응답 반환(주문 실패)
    end
    Order_Server->>Order_DB: 주문 히스토리 추가(유저id, 상품id, 주문개수)
    Order_DB-->>Order_Server: 주문 히스토리 추가 결과 반환
    opt 히스토리 추가 실패
    Order_Server-->User: 오류 응답 반환(주문 실패)
    end
    Order_Server-->-User: 응답 리턴(유저id, 상품id, 수량)
```