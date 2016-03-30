package com.epages;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import javaslang.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class BusinessUnitRefService {

    private final BusinessUnitRefRepository businessUnitRepository;
    private final BusinessUnitRefAccessor businessUnitAccessor;

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")
    }, ignoreExceptions = BusinessUnitNotFoundException.class)
    public BusinessUnit getBusinessUnit(Long tenantId) {

        Optional<BusinessUnit> businessUnitOptional = businessUnitRepository.findByTenantId(tenantId);

        return businessUnitOptional.map(b -> {
            log.info("business unit locally present for {} - using local copy", tenantId);
            return b;
        }).orElseGet(() ->{
            log.info("business unit for {} not present - retrieve it", tenantId);

            return Try.of(() -> businessUnitAccessor.getBusinessUnit(tenantId).get())
                    .onFailure(throwable -> {
                        if (throwable.getCause() instanceof BusinessUnitNotFoundException) {
                            BusinessUnitNotFoundException e = (BusinessUnitNotFoundException) throwable.getCause();
                            log.error("business unit not found", e);
                            throw e;
                        }
                        log.error("retrieval of businessunit failed", throwable);
                        throw new RuntimeException(throwable);
                    })
                    .get();
        });
    }

}
