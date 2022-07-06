package com.edemirkirkan.airqualityapi.pol.service.entityservice;

import com.edemirkirkan.airqualityapi.gen.base.BaseEntityService;
import com.edemirkirkan.airqualityapi.pol.dao.PolPollutionDao;
import com.edemirkirkan.airqualityapi.pol.entity.PolPollution;
import org.springframework.stereotype.Service;


@Service
public class PolPollutionEntityService extends BaseEntityService<PolPollution, PolPollutionDao> {

    public PolPollutionEntityService(PolPollutionDao polPollutionDao) {
        super(polPollutionDao);
    }

    public PolPollution findByDateAndCityName(String date, String name) {
        return getDao().findByDateAndCtyCity_Name(date, name);
    }
}
