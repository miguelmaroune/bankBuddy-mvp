package com.sg.bankBuddy.bankBuddy_core.adapter.api.controller;

import com.sg.bankBuddy.bankBuddy_core.adapter.api.dto.ErrorDto;
import com.sg.bankBuddy.bankBuddy_core.domain.exception.AccountNotFoundException;
import com.sg.bankBuddy.bankBuddy_core.domain.exception.ClientNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorDto> accountNotFoundException(AccountNotFoundException exception) {
        LOG.error("[Bank account management API] Account not Found !");
        return new ResponseEntity<>(ErrorDto.builder()
                .message(exception.getMessage())
                .errorCode(exception.getCode())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorDto> clientNotFoundException(ClientNotFoundException exception) {
        LOG.error("[Bank account management API] Account not Found !");
        return new ResponseEntity<>(ErrorDto.builder()
                .message(exception.getMessage())
                .errorCode(exception.getCode())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDto> illegalArgumentException(IllegalArgumentException exception) {
        LOG.error("[Bank account management API] Validation failed !");
        return new ResponseEntity<>(ErrorDto.builder()
                .message(exception.getMessage())
                .errorCode("500")
                .build(), HttpStatus.BAD_REQUEST);
    }

}
