package com.kal1van1ch.havetimerouting.exception;

import com.kal1van1ch.havetimerouting.model.dto.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RouteBuildErrorException.class)
    public ResponseEntity<ErrorDto> routeBuildErrorHandler(
            RouteBuildErrorException e
    ){
        logger.error(
                "Route building error",
                e
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Route building error"
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> notValidExceptionHandler(
            MethodArgumentNotValidException e
    ){
        logger.error(
                "Not valid params in input",
                e
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        new ErrorDto(
                                HttpStatus.BAD_REQUEST.value(),
                                "Not valid params in input"
                        )
                );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDto> invalidRequestBodyHandler(
            HttpMessageNotReadableException e
    ){
        logger.error(
                "Invalid request body",
                e
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        new ErrorDto(
                                HttpStatus.BAD_REQUEST.value(),
                                "Invalid request body"
                        )
                );
    }
}
