package com.epages;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessUnitRepository extends JpaRepository<BusinessUnit, Long> {
    Optional<BusinessUnit> findByTenantId(Long tenantId);
}
