package com.edemirkirkan.airqualityapi.cty.service.entityservice;

import com.edemirkirkan.airqualityapi.cty.dao.CtyCityDao;
import com.edemirkirkan.airqualityapi.cty.entity.CtyCity;
import com.edemirkirkan.airqualityapi.gen.entity.service.BaseEntityService;
import org.springframework.stereotype.Service;

@Service
public class CtyCityEntityService extends BaseEntityService<CtyCity, CtyCityDao> {

    public CtyCityEntityService(CtyCityDao ctyCityDao) {
        super(ctyCityDao);
    }

    public CtyCity findByName(String name) {
        return getDao().findByName(name);
    }
}
