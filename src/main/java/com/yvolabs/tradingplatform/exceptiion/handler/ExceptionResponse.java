package com.yvolabs.tradingplatform.exceptiion.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.Set;

/**
 * @author Yvonne N
 * @version 1.0
 * @since 24/10/2024
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExceptionResponse {

    private HttpStatus businessErrorCode;
    private String error;
    private Set<String> validationErrors;
    private Map<String, String> errors;
}
