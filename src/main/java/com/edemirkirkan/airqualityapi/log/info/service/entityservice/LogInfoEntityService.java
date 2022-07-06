package com.edemirkirkan.airqualityapi.log.info.service.entityservice;


import com.edemirkirkan.airqualityapi.gen.base.BaseEntityService;
import com.edemirkirkan.airqualityapi.log.info.dao.LogInfoDao;
import com.edemirkirkan.airqualityapi.log.info.entity.LogInfo;
import org.springframework.stereotype.Service;

@Service
public class LogInfoEntityService extends BaseEntityService<LogInfo, LogInfoDao> {

    public LogInfoEntityService(LogInfoDao dao) {
        super(dao);
    }
}
