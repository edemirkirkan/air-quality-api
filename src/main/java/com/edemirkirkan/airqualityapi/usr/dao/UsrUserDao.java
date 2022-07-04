package com.edemirkirkan.airqualityapi.usr.dao;

import com.edemirkirkan.airqualityapi.usr.entity.UsrUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsrUserDao extends JpaRepository<UsrUser, Long> {
    UsrUser findByUsername(String username);
}