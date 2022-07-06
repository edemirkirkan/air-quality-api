package com.edemirkirkan.airqualityapi.log.service.entityservice;


import com.edemirkirkan.airqualityapi.gen.base.BaseEntityService;
import com.edemirkirkan.airqualityapi.log.dao.LogInfoDao;
import com.edemirkirkan.airqualityapi.log.entity.LogInfo;
import org.springframework.stereotype.Service;

@Service
public class LogInfoEntityService extends BaseEntityService<LogInfo, LogInfoDao> {

    public LogInfoEntityService(LogInfoDao dao) {
        super(dao);
    }
}
