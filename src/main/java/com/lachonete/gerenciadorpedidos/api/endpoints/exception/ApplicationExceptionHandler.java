package com.lachonete.gerenciadorpedidos.api.endpoints.exception;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.lachonete.gerenciadorpedidos.entities.exception.InconsistenceOrderItemSubtotalException;
import com.lachonete.gerenciadorpedidos.ports.database.ProductGateway;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.MessageFormat;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String MESSAGE_NOT_READABLE_ERROR_MESSAGE_PATTERN = "Invalid format for property `{0}'";
    private static final String DEFAULT_CLIENT_ERROR_MESSAGE = "A client error occurred";
    private static final String DEFAULT_NOT_FOUND_MESSAGE = "We could not find that entity";
    private static final String PRICE_INCONSISTENCY_MESSAGE = "Sorry, we found a price inconsistency in request";

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleControllerException(final HttpServletRequest request, final Throwable throwable) {
        if (throwable instanceof ProductGateway.BadRequest) {
            return getErrorResponseForStatus(throwable, HttpStatus.BAD_REQUEST, DEFAULT_CLIENT_ERROR_MESSAGE);
        } else if (throwable instanceof ProductGateway.NotFound) {
            return getErrorResponseForStatus(throwable, HttpStatus.NOT_FOUND, DEFAULT_NOT_FOUND_MESSAGE);
        } else if (throwable instanceof InconsistenceOrderItemSubtotalException) {
            return getErrorResponseForStatus(throwable, HttpStatus.BAD_REQUEST, PRICE_INCONSISTENCY_MESSAGE);
        } else {
            return getDefaultErrorResponse(throwable);
        }
    }

    public ResponseEntity<ErrorResponse> getErrorResponseForStatus(Throwable ex, HttpStatus httpStatus, String defaultMessage) {
        String message = ErrorMessageMap.errors.getOrDefault(ex.getClass(), defaultMessage);
        return new ResponseEntity<>(
                ErrorResponse
                        .builder()
                        .addError(message)
                        .build(),
                httpStatus
        );
    }

    public ResponseEntity<ErrorResponse> getDefaultErrorResponse(final Throwable throwable) {
        logger.fatal("Unhandled exception ", throwable);
        return new ResponseEntity<>(
                ErrorResponse
                        .builder()
                        .addError("Oops! Something really bad happened and we couldn't recover.")
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            final HttpMessageNotReadableException ex,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request) {
        if (isInvalidFormatException(ex)) {
            return parseInvalidFormatException((InvalidFormatException) ex.getCause());
        } else if (isJsonParseException(ex)) {
            return parseJsonParseException((JsonParseException) ex.getCause());
        } else {
            return super.handleHttpMessageNotReadable(ex, headers, status, request);
        }
    }

    private boolean isInvalidFormatException(final HttpMessageNotReadableException ex) {
        return ex.getCause() instanceof InvalidFormatException;
    }

    public ResponseEntity<Object> parseInvalidFormatException(final InvalidFormatException ife) {
        String errorMessage = getErrorMessageForInvalidFormatException(ife);

        return formatErrorResponseForHttpMessageNotReadable(errorMessage);
    }

    public String getErrorMessageForInvalidFormatException(final InvalidFormatException ife) {
        logger.warn("Handling exception due to bad data ", ife);
        String errorMessage = MESSAGE_NOT_READABLE_ERROR_MESSAGE_PATTERN;
        try {
            return MessageFormat.format(
                    MESSAGE_NOT_READABLE_ERROR_MESSAGE_PATTERN,
                    CollectionUtils.isEmpty(ife.getPath())
                            ?
                            "" :
                            ife.getPath().get(0).getFieldName());
        } catch (Exception e) {
            logger.warn("Exception while constructing error message. Ignoring ", e);
            return errorMessage;
        }
    }

    public boolean isJsonParseException(final HttpMessageNotReadableException ex) {
        return ex.getCause() instanceof JsonParseException;
    }

    public ResponseEntity<Object> parseJsonParseException(final JsonParseException jpe) {
        String errorMessage = getErrorMessageForJsonParseException(jpe);

        return formatErrorResponseForHttpMessageNotReadable(errorMessage);
    }

    public String getErrorMessageForJsonParseException(final JsonParseException jpe) {
        logger.warn("Handling exception due to bad data ", jpe);
        String errorMessage = MESSAGE_NOT_READABLE_ERROR_MESSAGE_PATTERN;

        try {
            return MessageFormat.format(MESSAGE_NOT_READABLE_ERROR_MESSAGE_PATTERN,
                    jpe.getProcessor().getCurrentName());

        } catch (IOException e) {
            logger.error("Failed to get the current name for JsonParser processor ", e);
            return errorMessage;
        }
    }

    public ResponseEntity<Object> formatErrorResponseForHttpMessageNotReadable(final String errorMessage) {
        return new ResponseEntity<>(
                ErrorResponse
                        .builder()
                        .addError(errorMessage)
                        .build(),
                HttpStatus.BAD_REQUEST);
    }
}
