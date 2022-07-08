package com.edemirkirkan.airqualityapi.pol.converter;

import com.edemirkirkan.airqualityapi.pol.dto.PolPollutionDto;
import com.edemirkirkan.airqualityapi.pol.entity.PolPollution;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-08T08:41:55+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.11 (AdoptOpenJDK)"
)
@Component
public class PolPollutionMapperImpl extends PolPollutionMapper {

    @Override
    public PolPollution convertToPolPollution(PolPollutionDto polPollutionDto) {
        if ( polPollutionDto == null ) {
            return null;
        }

        PolPollution polPollution = new PolPollution();

        polPollution.setDate( polPollutionDto.getDate() );
        polPollution.setCtyCity( polPollutionDto.getCtyCity() );
        polPollution.setCo( polPollutionDto.getCo() );
        polPollution.setO3( polPollutionDto.getO3() );
        polPollution.setSo2( polPollutionDto.getSo2() );

        return polPollution;
    }
}
