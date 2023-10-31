package com.lachonete.gerenciadorpedidos.application.core.domain.exception;


public class InconsistencePriceException extends DomainException {

    public InconsistencePriceException(String message) {
        super(message);
    }

    public InconsistencePriceException(String message, Throwable cause) {
        super(message, cause);
    }
}
