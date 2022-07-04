package com.edemirkirkan.airqualityapi.cty.converter;

import com.edemirkirkan.airqualityapi.cty.dto.CtyCityDto;
import com.edemirkirkan.airqualityapi.cty.entity.CtyCity;
import com.edemirkirkan.airqualityapi.rest.dto.RestResponseGeoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class CtyCityMapper {

    @Mapping(target = "latitude", source = "lat")
    @Mapping(target = "longitude", source = "lon")
    @Mapping(target = "countryCode", source = "country")
    public abstract CtyCity convertToCtyCity(CtyCityDto ctyCityDto);

    public abstract CtyCityDto convertToCtyCityDto(RestResponseGeoDto restResponseGeoDto);
}
