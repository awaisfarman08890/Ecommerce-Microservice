package com.ecommerce.notification;


import com.ecommerce.notification.payload.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Consumer;

@Service
@Slf4j

public class OrderEventConsumer {
//    @RabbitListener(queues = "${rabbitmq.queue.name}")
//    public void handleOrderEvent(OrderCreatedEvent orderEvent) {
//         System.out.println("recived order evwent : " + orderEvent);
//
//         long orderId = (orderEvent.getOrderId());
//         OrderStatus orderstatus = orderEvent.getStatus();
//
//        System.out.println("orderstatus : " + orderstatus);
//        System.out.println("orderid : " + orderId);
//
//        // Update Database
//        // Send Notification
//        // Send Emails
//        // Generate Invoice
//        // Send seller notification
//    }
    @Bean
    public Consumer<OrderCreatedEvent> orderCreated() {
        return event -> {
            log.info("Recived order created OrderCreatedEvent: {}", event.getOrderId());
            log.info("Recived order created OrderCreatedEvent: {}", event.getUserId());

        };
    }
}
