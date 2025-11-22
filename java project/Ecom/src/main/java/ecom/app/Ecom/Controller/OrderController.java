package ecom.app.Ecom.Controller;

import ecom.app.Ecom.Service.OrderService;
import ecom.app.Ecom.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    @PostMapping
    public ResponseEntity<OrderResponse> createOne(
            @RequestHeader("X-User-ID") String userId){
       return orderService.createOrder(userId)
               .map(orderResponse -> new ResponseEntity<>(orderResponse,HttpStatus.CREATED))
               .orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));


    }


}
