package com.epages;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface BusinessUnitRefRepository extends JpaRepository<BusinessUnit, Long> {

    Optional<BusinessUnit> findByTenantId(Long tenantId);
}
