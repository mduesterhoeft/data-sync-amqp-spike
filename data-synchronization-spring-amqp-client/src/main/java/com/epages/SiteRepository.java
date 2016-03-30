package com.epages;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteRepository extends JpaRepository<Site, Long> {

    Optional<Site> findByTenantId(Long tenantId);
}
