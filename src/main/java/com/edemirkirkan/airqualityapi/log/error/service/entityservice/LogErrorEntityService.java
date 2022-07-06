package com.edemirkirkan.airqualityapi.log.error.service.entityservice;


import com.edemirkirkan.airqualityapi.gen.base.BaseEntityService;
import com.edemirkirkan.airqualityapi.log.error.dao.LogErrorDao;
import com.edemirkirkan.airqualityapi.log.error.entity.LogError;
import org.springframework.stereotype.Service;

@Service
public class LogErrorEntityService extends BaseEntityService<LogError, LogErrorDao> {

    public LogErrorEntityService(LogErrorDao dao) {
        super(dao);
    }
}
