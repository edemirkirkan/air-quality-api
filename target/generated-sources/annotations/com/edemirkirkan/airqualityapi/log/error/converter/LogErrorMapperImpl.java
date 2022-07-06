package com.edemirkirkan.airqualityapi.log.error.converter;

import com.edemirkirkan.airqualityapi.log.error.dto.LogErrorDto;
import com.edemirkirkan.airqualityapi.log.error.entity.LogError;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-06T18:24:42+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.11 (AdoptOpenJDK)"
)
@Component
public class LogErrorMapperImpl extends LogErrorMapper {

    @Override
    public LogError convertToLogError(LogErrorDto logErrorDto) {
        if ( logErrorDto == null ) {
            return null;
        }

        LogError logError = new LogError();

        logError.setHttpStatus( logErrorDto.getHttpStatus() );
        logError.setBody( logErrorDto.getBody() );
        logError.setHeaders( logErrorDto.getHeaders() );

        return logError;
    }
}
