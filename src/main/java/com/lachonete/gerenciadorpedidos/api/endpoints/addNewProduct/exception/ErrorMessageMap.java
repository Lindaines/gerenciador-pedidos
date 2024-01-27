package com.lachonete.gerenciadorpedidos.api.endpoints.addNewProduct.exception;


import com.lachonete.gerenciadorpedidos.ports.database.ProductGateway;

import java.util.HashMap;
import java.util.Map;

public class ErrorMessageMap {
    private ErrorMessageMap() {}

    public static Map<Class, String> errors = new HashMap<>();

    static {

    }
}
