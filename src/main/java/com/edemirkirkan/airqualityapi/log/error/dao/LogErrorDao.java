package com.edemirkirkan.airqualityapi.log.error.dao;

import com.edemirkirkan.airqualityapi.log.error.entity.LogError;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogErrorDao extends JpaRepository<LogError, Long> {
}
