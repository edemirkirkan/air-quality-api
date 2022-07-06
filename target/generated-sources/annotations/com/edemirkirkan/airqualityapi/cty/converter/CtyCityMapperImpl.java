package com.edemirkirkan.airqualityapi.cty.converter;

import com.edemirkirkan.airqualityapi.cty.dto.CtyCityDto;
import com.edemirkirkan.airqualityapi.cty.entity.CtyCity;
import com.edemirkirkan.airqualityapi.rest.dto.RestResponseGeoDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-05T16:42:13+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.15 (Oracle Corporation)"
)
@Component
public class CtyCityMapperImpl extends CtyCityMapper {

    @Override
    public CtyCity convertToCtyCity(CtyCityDto ctyCityDto) {
        if ( ctyCityDto == null ) {
            return null;
        }

        CtyCity ctyCity = new CtyCity();

        ctyCity.setLatitude( ctyCityDto.getLat() );
        ctyCity.setLongitude( ctyCityDto.getLon() );
        ctyCity.setCountryCode( ctyCityDto.getCountry() );
        ctyCity.setName( ctyCityDto.getName() );

        return ctyCity;
    }

    @Override
    public CtyCityDto convertToCtyCityDto(RestResponseGeoDto restResponseGeoDto) {
        if ( restResponseGeoDto == null ) {
            return null;
        }

        CtyCityDto ctyCityDto = new CtyCityDto();

        ctyCityDto.setName( restResponseGeoDto.getName() );
        ctyCityDto.setLat( restResponseGeoDto.getLat() );
        ctyCityDto.setLon( restResponseGeoDto.getLon() );
        ctyCityDto.setCountry( restResponseGeoDto.getCountry() );

        return ctyCityDto;
    }
}
