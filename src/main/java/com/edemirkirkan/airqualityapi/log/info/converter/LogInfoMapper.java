package com.edemirkirkan.airqualityapi.log.info.converter;

import com.edemirkirkan.airqualityapi.log.info.dto.LogInfoDto;
import com.edemirkirkan.airqualityapi.log.info.entity.LogInfo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class LogInfoMapper {

    public abstract LogInfo covertToLogInfo(LogInfoDto logInfoDto);
}
