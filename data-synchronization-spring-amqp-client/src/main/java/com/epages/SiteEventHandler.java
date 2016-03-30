package com.epages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RepositoryEventHandler
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class SiteEventHandler {

    private final BusinessUnitRefService businessUnitRefService;

    @HandleBeforeCreate
    public void initializeSite(Site site) {
        site.setBusinessUnitRef(businessUnitRefService.getBusinessUnit(site.getTenantId()));
    }

}
