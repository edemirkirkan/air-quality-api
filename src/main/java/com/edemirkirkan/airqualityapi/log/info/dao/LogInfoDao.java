package com.edemirkirkan.airqualityapi.log.info.dao;

import com.edemirkirkan.airqualityapi.log.info.entity.LogInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogInfoDao extends JpaRepository<LogInfo, Long> {
}
