package com.edemirkirkan.airqualityapi.pol.service;

import com.edemirkirkan.airqualityapi.cty.converter.CtyCityMapper;
import com.edemirkirkan.airqualityapi.cty.dto.CtyCityDto;
import com.edemirkirkan.airqualityapi.cty.entity.CtyCity;
import com.edemirkirkan.airqualityapi.cty.service.CtyCityService;
import com.edemirkirkan.airqualityapi.gen.exceptions.BusinessException;
import com.edemirkirkan.airqualityapi.gen.utils.DateUtil;
import com.edemirkirkan.airqualityapi.pol.converter.PolPollutionConverter;
import com.edemirkirkan.airqualityapi.pol.converter.PolPollutionMapper;
import com.edemirkirkan.airqualityapi.pol.dto.PolPollutionDto;
import com.edemirkirkan.airqualityapi.pol.entity.PolPollution;
import com.edemirkirkan.airqualityapi.pol.enums.EnumPolPollutionErrorMessage;
import com.edemirkirkan.airqualityapi.rest.dto.RestResponseGeoDto;
import com.edemirkirkan.airqualityapi.rest.dto.RestResponsePolDayDto;
import com.edemirkirkan.airqualityapi.rest.service.RestService;
import com.edemirkirkan.airqualityapi.rest.dto.RestResponsePolDto;
import com.edemirkirkan.airqualityapi.pol.dto.PolPollutionResponseDto;
import com.edemirkirkan.airqualityapi.pol.service.entityservice.PolPollutionEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class PolPollutionService {
    private final PolPollutionEntityService polPollutionEntityService;
    private final PolPollutionMapper polPollutionMapper;
    private final PolPollutionConverter polPollutionConverter;
    private final CtyCityService ctyCityService;
    private final RestService restService;
    private final CtyCityMapper ctyCityMapper;

    public PolPollutionResponseDto getPollutionData(String city, String startDateStr, String endDateStr) {
        Date startDate = DateUtil.atStartOfDay(DateUtil.stringToDate(startDateStr));
        Date endDate = DateUtil.atEndOfDay(DateUtil.stringToDate(endDateStr));
        validateApiCutoffDate(startDate, endDate);


        CtyCity ctyCity = checkDatabaseIfCityExists(city);
        // if database does not contain city, then query OpenWeather Geocoding API
        if (ctyCity == null) {
            CtyCityDto ctyCityDto = queryApiForCity(city);
            ctyCity = ctyCityService.save(ctyCityDto);
            log.info("ahmet");
        }


        // given period of dates by user
        List<Date> daysBetween = DateUtil.getDatesInRange(startDate, endDate);
        // response list that is later converted to response dto and to be showed to user
        List<PolPollution> polPollutionList = new ArrayList<>();
        // indices of entries that is already existing in db
        List<Integer> foundIndices = new ArrayList<>();

        for (int i = 0, n = daysBetween.size(); i < n; i++) {
            PolPollution polPollution = polPollutionEntityService.findByDateAndCityName(
                    DateUtil.dateToString(daysBetween.get(i)), city);
            // if database does contain the pollutant information, add it to the response list
            // and also save its index for using in the later calculation regarding which period of
            // dates are not in database, and thus, should be queried from OpenWeather Geocoding API
            if (polPollution != null) {
                foundIndices.add(i);
                polPollutionList.add(polPollution);
            }
        }


        // calculate which periods of dates do not exist in db, and query the API for those periods
        List<Pair<Integer, Integer>> periodsToQuery = findDatePeriodsToQuery(daysBetween, foundIndices);
        polPollutionList.addAll(queryTheApiForPeriodsNotInDb(ctyCity, daysBetween, periodsToQuery));


        sortByDate(polPollutionList);

        PolPollutionResponseDto response = polPollutionConverter.convertToPolPollutionResponseDto(polPollutionList);
        return response;
    }

    private List<Pair<Integer, Integer>> findDatePeriodsToQuery(List<Date> daysBetween,
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

    private List<PolPollution> queryTheApiForPeriodsNotInDb(CtyCity ctyCity, List<Date> daysBetween, List<Pair<Integer, Integer>> periodsToQuery) {
        List<PolPollution> entityList = new ArrayList<>();
        for (Pair<Integer, Integer> period : periodsToQuery) {
            RestResponsePolDto restResponsePolDto = restService.polRequest(ctyCity.getLatitude(),
                    ctyCity.getLongitude(), daysBetween.get(period.getFirst()), daysBetween.get(period.getSecond()));
            List<RestResponsePolDayDto> restResponsePolDayDtoList = restResponsePolDto.getList();
            entityList.addAll(retrieveEntityList(ctyCity, restResponsePolDayDtoList));
        }
        return entityList;
    }

    private List<PolPollution> retrieveEntityList(CtyCity ctyCity, List<RestResponsePolDayDto> restResponsePolDayDtoList) {
        Map<String, BigDecimal> mapCO = new TreeMap<>();
        Map<String, BigDecimal> mapO3 = new TreeMap<>();
        Map<String, BigDecimal> mapSO2 = new TreeMap<>();
        Map<String, BigDecimal> hourCount = new TreeMap<>();
        return calculateAverage(restResponsePolDayDtoList, ctyCity, mapCO, mapO3, mapSO2, hourCount);
    }

    public void deletePollutionData(String city, String date) {
        PolPollution polPollution = polPollutionEntityService.findByDateAndCityName(date, city);
        if (polPollution == null) {
            throw new BusinessException(EnumPolPollutionErrorMessage.ENTRY_CANNOT_FOUND);
        }

        polPollutionEntityService.delete(polPollution);
    }

    private List<PolPollution> calculateAverage(List<RestResponsePolDayDto> restResponsePolDayDtoList, CtyCity ctyCity,
                                                Map<String, BigDecimal> mapCO, Map<String, BigDecimal> mapO3,
                                                Map<String, BigDecimal> mapSO2, Map<String, BigDecimal> hourCount) {
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
            polPollutionList.add(polPollution);
        }
        return polPollutionList;
    }

    private PolPollutionDto buildPolPollutionDto(CtyCity ctyCity, Map<String, BigDecimal> mapCO, Map<String, BigDecimal> mapO3, Map<String, BigDecimal> mapSO2, Map<String, BigDecimal> hourCount, String currentDay) {
        PolPollutionDto polPollutionDto = new PolPollutionDto();
        polPollutionDto.setCtyCity(ctyCity);
        polPollutionDto.setDate(currentDay);
        polPollutionDto.setCo(mapCO.get(currentDay).divide(hourCount.get(currentDay), 20, RoundingMode.DOWN));
        polPollutionDto.setO3(mapO3.get(currentDay).divide(hourCount.get(currentDay), 20, RoundingMode.DOWN));
        polPollutionDto.setSo2(mapSO2.get(currentDay).divide(hourCount.get(currentDay), 20, RoundingMode.DOWN));
        return polPollutionDto;
    }


    private void validateApiCutoffDate(Date startDate, Date endDate) {
        LocalDateTime apiCutOffDate = LocalDateTime.of(2020, Month.NOVEMBER,27,0, 0);

        if (DateUtil.dateToLocalDate(startDate).isBefore(ChronoLocalDate.from(apiCutOffDate))
                || DateUtil.dateToLocalDate(endDate).isBefore(ChronoLocalDate.from(apiCutOffDate)) ) {
            throw new BusinessException(EnumPolPollutionErrorMessage.DATE_BEFORE_CUTOFF_CANNOT_BE_QUERIED);
        }
    }

    private void sortByDate(List<PolPollution> polPollutionList) {
        polPollutionList.sort(Comparator.comparing(o -> DateUtil.stringToDate(o.getDate())));
    }

    private CtyCity checkDatabaseIfCityExists(String cityName) {
        return ctyCityService.findByName(cityName);
    }

    private CtyCityDto queryApiForCity(String cityName) {
        RestResponseGeoDto restResponseGeoDto = restService.geoRequest(cityName);
        CtyCityDto ctyCityDto = ctyCityMapper.convertToCtyCityDto(restResponseGeoDto);
        return ctyCityDto;
    }

}
