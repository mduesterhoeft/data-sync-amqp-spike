package com.epages;

import static com.epages.MessagingConfig.DATA_SYNC_TEST_EXCHANGE;

import java.util.Optional;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class InitMessageSubscriber {

    private final BusinessUnitRepository businessUnitRepository;

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue("data-sync-init-queue"),
                    exchange = @Exchange(value = DATA_SYNC_TEST_EXCHANGE, durable = "true", type = "topic"),
                    key = "businessunit.businessunit.send-init"
            )})
    public BusinessUnitRefResponse getInitData(BusinessUnitRefRequest request) {
        log.info("getInitData {}", request);

        sleep();
        Optional<BusinessUnit> byTenantId = businessUnitRepository.findByTenantId(request.getTenantId());
        return byTenantId
                .map(BusinessUnitRefResponse::new)
                .orElse(new BusinessUnitRefResponse());
    }

    private void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
