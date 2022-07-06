package com.edemirkirkan.airqualityapi.usr.service.entityservice;

import com.edemirkirkan.airqualityapi.gen.base.BaseEntityService;
import com.edemirkirkan.airqualityapi.usr.dao.UsrUserDao;
import com.edemirkirkan.airqualityapi.usr.entity.UsrUser;
import org.springframework.stereotype.Service;

@Service
public class UsrUserEntityService extends BaseEntityService<UsrUser, UsrUserDao> {

    public UsrUserEntityService(UsrUserDao dao) {
        super(dao);
    }

    public UsrUser findByUsername(String username){
        return getDao().findByUsername(username);
    }
}