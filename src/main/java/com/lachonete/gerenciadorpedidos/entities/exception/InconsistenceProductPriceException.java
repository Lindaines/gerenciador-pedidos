package com.lachonete.gerenciadorpedidos.entities.exception;


public class InconsistenceProductPriceException extends DomainException {

    public InconsistenceProductPriceException(String message) {
        super(message);
    }

    public InconsistenceProductPriceException(String message, Throwable cause) {
        super(message, cause);
    }
}
