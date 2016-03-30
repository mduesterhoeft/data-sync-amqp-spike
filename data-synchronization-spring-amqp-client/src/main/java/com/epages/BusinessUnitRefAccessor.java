package com.epages;

import static com.epages.MessagingConfig.DATA_SYNC_TEST_EXCHANGE;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class BusinessUnitRefAccessor {

    private final AmqpTemplate amqpTemplate;
    private final BusinessUnitRefRepository businessUnitRefRepository;


    public Future<BusinessUnit> getBusinessUnit(Long tenantId) {
        log.info("initializeSite getting init data for {}", tenantId);
        return CompletableFuture.supplyAsync(() -> {
            BusinessUnitRefResponse response = (BusinessUnitRefResponse) amqpTemplate.convertSendAndReceive(DATA_SYNC_TEST_EXCHANGE,
                    "businessunit.businessunit.send-init", new BusinessUnitRefRequest(tenantId));
            log.info("got {}", response);
            if (response.getBusinessUnit() != null) {
                return businessUnitRefRepository.save(response.getBusinessUnit());
            } else {
                throw new BusinessUnitNotFoundException(tenantId);
            }
        });
    }
}
