package com.gupta.booking.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIException {
    private String message;
    private Integer statusCode;
}
