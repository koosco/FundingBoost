package practice.fundingboost2.item.order.repo.entity;

public enum OrderStatus {
    PENDING_PAYMENT, // 결제 대기
    PENDING,   // 주문 접수
    PROCESSING, // 배송 준비 중
    SHIPPED,    // 배송 중
    DELIVERED,  // 배송 완료
    CANCELED;   // 취소됨
}
