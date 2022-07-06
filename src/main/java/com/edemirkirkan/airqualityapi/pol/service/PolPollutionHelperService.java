package com.edemirkirkan.airqualityapi.pol.service;

import com.edemirkirkan.airqualityapi.cty.converter.CtyCityMapper;
import com.edemirkirkan.airqualityapi.cty.dto.CtyCityDto;
import com.edemirkirkan.airqualityapi.cty.entity.CtyCity;
import com.edemirkirkan.airqualityapi.cty.service.CtyCityService;
import com.edemirkirkan.airqualityapi.gen.exceptions.BusinessException;
import com.edemirkirkan.airqualityapi.gen.utils.DateUtil;
import com.edemirkirkan.airqualityapi.log.info.dto.LogInfoDto;
import com.edemirkirkan.airqualityapi.log.info.enums.EnumLogInfoActionType;
import com.edemirkirkan.airqualityapi.log.info.enums.EnumLogInfoDataSourceType;
import com.edemirkirkan.airqualityapi.log.info.enums.EnumLogInfoEntityType;
import com.edemirkirkan.airqualityapi.log.info.service.LogInfoService;
import com.edemirkirkan.airqualityapi.pol.converter.PolPollutionMapper;
import com.edemirkirkan.airqualityapi.pol.dto.PolPollutionDto;
import com.edemirkirkan.airqualityapi.pol.entity.PolPollution;
import com.edemirkirkan.airqualityapi.pol.enums.EnumPolPollutionErrorMessage;
import com.edemirkirkan.airqualityapi.pol.service.entityservice.PolPollutionEntityService;
import com.edemirkirkan.airqualityapi.rest.dto.RestResponseGeoDto;
import com.edemirkirkan.airqualityapi.rest.dto.RestResponsePolDayDto;
import com.edemirkirkan.airqualityapi.rest.dto.RestResponsePolDto;
import com.edemirkirkan.airqualityapi.rest.service.RestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.chrono.ChronoLocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PolPollutionHelperService {

    private final PolPollutionEntityService polPollutionEntityService;
    private final PolPollutionMapper polPollutionMapper;
    private final CtyCityService ctyCityService;
    private final RestService restService;
    private final CtyCityMapper ctyCityMapper;
    private final LogInfoService logInfoService;

    protected List<Pair<Integer, Integer>> findDatePeriodsToQuery(List<Date> daysBetween,
                                                                List<Integer> foundIndices) {
        List<Pair<Integer, Integer>> periodsToQuery= new ArrayList<>();
        int prev = -1;
        for (Integer index : foundIndices) {
            int  candidateStartIndex = prev + 1;
            int candidateEndIndex = index - 1;
            if (candidateStartIndex <= candidateEndIndex) {
                periodsToQuery.add(Pair.of(candidateStartIndex, candidateEndIndex ));
            }
            prev = index;
        }
        if (foundIndices.size() == 0) {
            periodsToQuery.add(Pair.of(0, daysBetween.size() - 1));
        }
        else if (foundIndices.get(foundIndices.size() - 1) + 1 <= daysBetween.size() - 1){
            int lastFoundIndex = foundIndices.get(foundIndices.size() - 1) + 1;
            int lastIndexOfTheAllPeriod = daysBetween.size() - 1;
            periodsToQuery.add(Pair.of(lastFoundIndex, lastIndexOfTheAllPeriod));
        }
        return periodsToQuery;
    }

    protected List<PolPollution> queryTheApiForPeriodsNotInDb(CtyCity ctyCity, List<Date> daysBetween, List<Pair<Integer, Integer>> periodsToQuery) {
        List<PolPollution> entityList = new ArrayList<>();
        for (Pair<Integer, Integer> period : periodsToQuery) {
            Date startDate = daysBetween.get(period.getFirst());
            Date endDate = daysBetween.get(period.getSecond());
            logMsg("Querying OpenWeatherApi for the city " + ctyCity.getName() + " between the dates ("
                    + DateUtil.dateToString(startDate) + " and " + DateUtil.dateToString(endDate) + ") ...");
            RestResponsePolDto restResponsePolDto = restService.polRequest(ctyCity.getLatitude(),
                    ctyCity.getLongitude(), startDate, endDate);
            logMsg("Pollutant data for the city " + ctyCity.getName() + " between the dates (" + DateUtil.dateToString
                    (startDate) + " and " + DateUtil.dateToString(endDate) + ") is retrieved from OpenWeatherApi");
            List<RestResponsePolDayDto> restResponsePolDayDtoList = restResponsePolDto.getList();
            entityList.addAll(retrieveEntityList(ctyCity, restResponsePolDayDtoList));
        }
        return entityList;
    }

    protected List<PolPollution> retrieveEntityList(CtyCity ctyCity, List<RestResponsePolDayDto> restResponsePolDayDtoList) {
        Map<String, BigDecimal> mapCO = new TreeMap<>();
        Map<String, BigDecimal> mapO3 = new TreeMap<>();
        Map<String, BigDecimal> mapSO2 = new TreeMap<>();
        Map<String, BigDecimal> hourCount = new TreeMap<>();
        return calculateAverageAndSave(restResponsePolDayDtoList, ctyCity, mapCO, mapO3, mapSO2, hourCount);
    }

    protected List<PolPollution> calculateAverageAndSave(List<RestResponsePolDayDto> restResponsePolDayDtoList,
                                                       CtyCity ctyCity, Map<String, BigDecimal> mapCO,
                                                       Map<String, BigDecimal> mapO3,
                                                       Map<String, BigDecimal> mapSO2,
                                                       Map<String, BigDecimal> hourCount) {
        List<PolPollution> polPollutionList = new ArrayList<>();
        for (RestResponsePolDayDto day : restResponsePolDayDtoList) {
            String currentDay =  DateUtil.timestampToString(day.getDt());
            mapCO.put(currentDay, mapCO.getOrDefault(currentDay, BigDecimal.ZERO).add(day.getComponents().getCo()));
            mapO3.put(currentDay, mapO3.getOrDefault(currentDay, BigDecimal.ZERO).add(day.getComponents().getO3()));
            mapSO2.put(currentDay, mapSO2.getOrDefault(currentDay, BigDecimal.ZERO).add(day.getComponents().getSo2()));
            hourCount.put(currentDay, hourCount.getOrDefault(currentDay, BigDecimal.ZERO).add(BigDecimal.ONE));
        }
        for (String currentDay : hourCount.keySet()) {
            PolPollutionDto polPollutionDto = buildPolPollutionDto(ctyCity, mapCO, mapO3, mapSO2, hourCount, currentDay);
            PolPollution polPollution = polPollutionMapper.convertToPolPollution(polPollutionDto);
            polPollution = polPollutionEntityService.save(polPollution);
            logAction(ctyCity.getName(), EnumLogInfoEntityType.PollutantData, EnumLogInfoActionType.saved,
                    EnumLogInfoDataSourceType.database, currentDay);
            polPollutionList.add(polPollution);
        }
        return polPollutionList;
    }

    protected PolPollutionDto buildPolPollutionDto(CtyCity ctyCity, Map<String, BigDecimal> mapCO, Map<String, BigDecimal> mapO3, Map<String, BigDecimal> mapSO2, Map<String, BigDecimal> hourCount, String currentDay) {
        PolPollutionDto polPollutionDto = new PolPollutionDto();
        polPollutionDto.setCtyCity(ctyCity);
        polPollutionDto.setDate(currentDay);
        polPollutionDto.setCo(mapCO.get(currentDay).divide(hourCount.get(currentDay), 20, RoundingMode.DOWN));
        polPollutionDto.setO3(mapO3.get(currentDay).divide(hourCount.get(currentDay), 20, RoundingMode.DOWN));
        polPollutionDto.setSo2(mapSO2.get(currentDay).divide(hourCount.get(currentDay), 20, RoundingMode.DOWN));
        return polPollutionDto;
    }


    protected void validateApiCutoffDate(Date startDate, Date endDate) {
        LocalDateTime apiCutOffDate = LocalDateTime.of(2020, Month.NOVEMBER,27,0, 0);

        if (DateUtil.dateToLocalDate(startDate).isBefore(ChronoLocalDate.from(apiCutOffDate))
                || DateUtil.dateToLocalDate(endDate).isBefore(ChronoLocalDate.from(apiCutOffDate)) ) {
            throw new BusinessException(EnumPolPollutionErrorMessage.DATE_BEFORE_CUTOFF_CANNOT_BE_QUERIED);
        }
    }

    protected void sortByDate(List<PolPollution> polPollutionList) {
        polPollutionList.sort(Comparator.comparing(o -> DateUtil.stringToDate(o.getDate())));
    }

    protected CtyCity checkDatabaseIfCityExists(String cityName) {
        return ctyCityService.findByName(cityName);
    }

    protected CtyCityDto queryApiForCity(String cityName) {
        RestResponseGeoDto restResponseGeoDto = restService.geoRequest(cityName);
        return ctyCityMapper.convertToCtyCityDto(restResponseGeoDto);
    }

    protected void logAction(String city, EnumLogInfoEntityType entityType, EnumLogInfoActionType actionType,
                           EnumLogInfoDataSourceType dataSourceType, String polPollutionDate) {
        LogInfoDto logInfoDto = LogInfoDto.builder()
                .entity(entityType)
                .action(actionType)
                .source(dataSourceType)
                .ctyCityName(city)
                .polPollutionDate(polPollutionDate)
                .build();
        logInfoService.logInfoAndSave(logInfoDto);
    }

    protected void logMsg(String msg) {
        logInfoService.logInfo(msg);
    }
}
