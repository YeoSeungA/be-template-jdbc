package com.springboot.order.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.web.util.pattern.PathPattern;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Table("ORDERS")
public class Order {
    @Id
    private long orderId;
    private long memberId;
//    private long coffeeId;

    @MappedCollection(idColumn = "ORDER_ID")
    private Set<OrderCoffee> orderCoffeees = new LinkedHashSet<>();

    private LocalDateTime createdAt = LocalDateTime.now();

    private OrderStatus orderStatus = OrderStatus.ORDER_REQUEST;    // Enum Class 상태는 상수로 관리

    public enum OrderStatus {
        ORDER_REQUEST(1, "주문 요청"),
        ORDER_CONFIRM(2, "주문 확정"),
        ORDER_COMPLETE(3, "주문 완료"),
        ORDER_CANCEL(4, "주문 취소");

        @Getter
        private int stepNumber;
        @Getter
        private String stepDescription;

        OrderStatus(int stepNumber, String stepDescription) {
            this.stepNumber = stepNumber;
            this.stepDescription = stepDescription;
        }
    }


}
