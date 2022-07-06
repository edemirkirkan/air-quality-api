package com.edemirkirkan.airqualityapi.log.error.service;

import com.edemirkirkan.airqualityapi.log.error.converter.LogErrorMapper;
import com.edemirkirkan.airqualityapi.log.error.dto.LogErrorDto;
import com.edemirkirkan.airqualityapi.log.error.entity.LogError;
import com.edemirkirkan.airqualityapi.log.error.service.entityservice.LogErrorEntityService;
import com.edemirkirkan.airqualityapi.sec.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogErrorService {

    private final LogErrorEntityService logErrorEntityService;
    private final LogErrorMapper logErrorMapper;

    public void logErrorAndSave(LogErrorDto logInfoDto){
        LogError logError = logErrorMapper.convertToLogError(logInfoDto);
        logError.setUsrUserId(AuthenticationService.currentUserId());
        logError.setErrorDate(new Date());

        logError = logErrorEntityService.save(logError);

        log.error(logError.getBody());
    }
}
