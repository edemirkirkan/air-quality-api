package com.edemirkirkan.airqualityapi.log.error.converter;

import com.edemirkirkan.airqualityapi.log.error.dto.LogErrorDto;
import com.edemirkirkan.airqualityapi.log.error.entity.LogError;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class LogErrorMapper {
    public abstract LogError convertToLogError(LogErrorDto logErrorDto);
}
