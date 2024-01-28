package com.lachonete.gerenciadorpedidos.usecases.product.add;

import com.lachonete.gerenciadorpedidos.ports.usescases.product.add.AddProductRequest;

public interface AddProductInputBoundary {
    void execute(AddProductRequest request);
}
