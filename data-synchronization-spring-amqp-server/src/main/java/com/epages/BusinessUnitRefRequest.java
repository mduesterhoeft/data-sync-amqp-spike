package com.epages;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BusinessUnitRefRequest {

    @NotNull @NonNull
    private Long tenantId;
}
