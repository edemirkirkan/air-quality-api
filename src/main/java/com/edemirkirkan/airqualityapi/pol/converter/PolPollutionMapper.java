package com.edemirkirkan.airqualityapi.pol.converter;

import com.edemirkirkan.airqualityapi.pol.dto.PolPollutionDto;
import com.edemirkirkan.airqualityapi.pol.entity.PolPollution;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class PolPollutionMapper {

    public abstract PolPollution convertToPolPollution(PolPollutionDto polPollutionDto);

}