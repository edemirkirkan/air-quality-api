package com.edemirkirkan.airqualityapi.pol.dao;

import com.edemirkirkan.airqualityapi.pol.entity.PolPollution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolPollutionDao extends JpaRepository<PolPollution, Long> {

    PolPollution findByDateAndCtyCity_Name(String date, String name);

}
