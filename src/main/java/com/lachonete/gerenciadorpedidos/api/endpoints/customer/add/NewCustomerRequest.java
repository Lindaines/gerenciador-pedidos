package com.lachonete.gerenciadorpedidos.api.endpoints.customer.add;

import com.lachonete.gerenciadorpedidos.entities.valueobject.ProductCategory;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class NewCustomerRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String cpf;
    @NotBlank
    private String email;
}
