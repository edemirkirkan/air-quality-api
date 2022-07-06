package com.edemirkirkan.airqualityapi.log.dao;

import com.edemirkirkan.airqualityapi.log.entity.LogInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogInfoDao extends JpaRepository<LogInfo, Long> {
}
