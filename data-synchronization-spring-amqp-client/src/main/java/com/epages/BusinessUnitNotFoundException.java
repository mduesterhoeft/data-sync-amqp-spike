package com.epages;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BusinessUnitNotFoundException extends RuntimeException {
    public BusinessUnitNotFoundException(Long tenantId) {
        super(String.format("business unit not found for tenantId %s", tenantId));
    }
}
