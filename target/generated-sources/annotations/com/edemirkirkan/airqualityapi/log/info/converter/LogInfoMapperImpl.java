package com.edemirkirkan.airqualityapi.log.info.converter;

import com.edemirkirkan.airqualityapi.log.info.dto.LogInfoDto;
import com.edemirkirkan.airqualityapi.log.info.entity.LogInfo;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-07T04:22:00+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.11 (AdoptOpenJDK)"
)
@Component
public class LogInfoMapperImpl extends LogInfoMapper {

    @Override
    public LogInfo covertToLogInfo(LogInfoDto logInfoDto) {
        if ( logInfoDto == null ) {
            return null;
        }

        LogInfo logInfo = new LogInfo();

        logInfo.setEntity( logInfoDto.getEntity() );
        logInfo.setAction( logInfoDto.getAction() );
        logInfo.setSource( logInfoDto.getSource() );
        logInfo.setCtyCityName( logInfoDto.getCtyCityName() );
        logInfo.setPolPollutionDate( logInfoDto.getPolPollutionDate() );

        return logInfo;
    }
}
