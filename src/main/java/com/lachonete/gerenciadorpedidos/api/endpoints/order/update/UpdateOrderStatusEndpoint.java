package com.lachonete.gerenciadorpedidos.api.endpoints.order.update;


import com.lachonete.gerenciadorpedidos.api.endpoints.BaseEndpoint;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.update.UpdateOrderInputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.update.UpdateOrderRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class UpdateOrderStatusEndpoint implements BaseEndpoint {
    private final UpdateOrderInputBoundary useCase;

    public UpdateOrderStatusEndpoint(UpdateOrderInputBoundary useCase) {
        this.useCase = useCase;
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity execute(@PathVariable final UUID orderId, @Valid @RequestBody UpdateOrderStatusRequest request){
        UpdateOrderRequest updateRequest = UpdateOrderRequest.builder().id(orderId).status(request.getOrderStatus()).build();
        useCase.execute(updateRequest);
        return ResponseEntity.ok().build();

    }
}
