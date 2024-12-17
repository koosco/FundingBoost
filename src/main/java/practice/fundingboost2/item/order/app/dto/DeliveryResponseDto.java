package practice.fundingboost2.item.order.app.dto;

import practice.fundingboost2.item.order.repo.entity.Delivery;

public record DeliveryResponseDto(Long deliveryId, String userName, String address, String phoneNumber) {

    public DeliveryResponseDto(Delivery delivery) {
        this(delivery.getId(), delivery.getUserName(), delivery.getAddress(), delivery.getPhoneNumber());
    }
}
