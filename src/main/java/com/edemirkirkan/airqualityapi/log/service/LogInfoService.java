package com.edemirkirkan.airqualityapi.log.service;

import com.edemirkirkan.airqualityapi.log.dto.LogInfoDto;
import com.edemirkirkan.airqualityapi.log.entity.LogInfo;
import com.edemirkirkan.airqualityapi.log.service.entityservice.LogInfoEntityService;
import com.edemirkirkan.airqualityapi.sec.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogInfoService {

    private final LogInfoEntityService logInfoEntityService;

    public void saveLog(LogInfoDto logInfoDto){

        LogInfo logInfo = new LogInfo();
        logInfo.setUsrUserId(AuthenticationService.currentUserId());
        logInfo.setErrorDate(new Date());
        logInfo.setHttpStatus(logInfoDto.getHttpStatus());
        logInfo.setHeaders(logInfoDto.getHeaders());
        logInfo.setBody(logInfoDto.getBody());
        logInfo = logInfoEntityService.save(logInfo);

        log.error(logInfo.getBody());
    }
}
