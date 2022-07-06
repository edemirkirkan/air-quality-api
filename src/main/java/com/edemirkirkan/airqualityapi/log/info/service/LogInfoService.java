package com.edemirkirkan.airqualityapi.log.info.service;

import com.edemirkirkan.airqualityapi.log.info.converter.LogInfoMapper;
import com.edemirkirkan.airqualityapi.log.info.dto.LogInfoDto;
import com.edemirkirkan.airqualityapi.log.info.entity.LogInfo;
import com.edemirkirkan.airqualityapi.log.info.enums.EnumLogInfoActionType;
import com.edemirkirkan.airqualityapi.log.info.enums.EnumLogInfoEntityType;
import com.edemirkirkan.airqualityapi.log.info.service.entityservice.LogInfoEntityService;
import com.edemirkirkan.airqualityapi.sec.auth.AuthenticationService;
import com.edemirkirkan.airqualityapi.usr.entity.UsrUser;
import com.edemirkirkan.airqualityapi.usr.service.entityservice.UsrUserEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogInfoService {

    private final LogInfoEntityService logInfoEntityService;
    private final LogInfoMapper logInfoMapper;
    private final UsrUserEntityService usrUserEntityService;

    public void logInfoAndSave(LogInfoDto logInfoDto){
        LogInfo logInfo = logInfoMapper.covertToLogInfo(logInfoDto);
        Long currentUserId = AuthenticationService.currentUserId();
        logInfo.setUsrUserId(currentUserId);

        logInfo = logInfoEntityService.save(logInfo);
        String cityName = logInfo.getCtyCityName();
        String pronoun = buildLogPronoun(logInfo);
        String subject = buildLogSubject(logInfo, cityName);
        String userText = buildLogUserText(currentUserId);

        log.info(logInfo.getEntity() + " (" + subject + ") is " +
                logInfo.getAction() + pronoun + logInfo.getSource() +
                userText + " at time '" + logInfo.getBaseAdditionalFields().getUpdateDate() + "'");
    }

    private String buildLogPronoun(LogInfo logInfo) {
        return logInfo.getAction().equals(EnumLogInfoActionType.saved) ? " to " : " from ";
    }

    private String buildLogSubject(LogInfo logInfo, String cityName) {
        return logInfo.getEntity().equals(EnumLogInfoEntityType.City) ?
                cityName : cityName + " - " + logInfo.getPolPollutionDate();
    }

    private String buildLogUserText(Long currentUserId) {
        Optional<UsrUser> user = usrUserEntityService.findById(currentUserId);
        String userName = user.map(UsrUser::getUsername).orElse(null);
        return userName != null ? " by user named '" + userName + "'" : "";
    }

    public void logInfo(String msg) {
        log.info(msg);
    }
}









