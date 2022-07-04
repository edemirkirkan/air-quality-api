package com.edemirkirkan.airqualityapi.cty.dao;

import com.edemirkirkan.airqualityapi.cty.entity.CtyCity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CtyCityDao extends JpaRepository<CtyCity, Long> {
    CtyCity findByName(String name);
}
