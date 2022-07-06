package com.edemirkirkan.airqualityapi.log.info.dto;

import com.edemirkirkan.airqualityapi.log.info.enums.EnumLogInfoActionType;
import com.edemirkirkan.airqualityapi.log.info.enums.EnumLogInfoDataSourceType;
import com.edemirkirkan.airqualityapi.log.info.enums.EnumLogInfoEntityType;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Builder
public class LogInfoDto {
    private EnumLogInfoEntityType entity;
    private EnumLogInfoActionType action;
    private EnumLogInfoDataSourceType source;
    private String ctyCityName;
    private String polPollutionDate;
}