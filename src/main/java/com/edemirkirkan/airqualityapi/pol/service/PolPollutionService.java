package com.edemirkirkan.airqualityapi.pol.service;

import com.edemirkirkan.airqualityapi.cty.dto.CtyCityDto;
import com.edemirkirkan.airqualityapi.cty.entity.CtyCity;
import com.edemirkirkan.airqualityapi.cty.service.CtyCityService;
import com.edemirkirkan.airqualityapi.gen.exceptions.BusinessException;
import com.edemirkirkan.airqualityapi.gen.utils.DateUtil;
import com.edemirkirkan.airqualityapi.log.info.enums.EnumLogInfoActionType;
import com.edemirkirkan.airqualityapi.log.info.enums.EnumLogInfoDataSourceType;
import com.edemirkirkan.airqualityapi.log.info.enums.EnumLogInfoEntityType;
import com.edemirkirkan.airqualityapi.pol.converter.PolPollutionConverter;
import com.edemirkirkan.airqualityapi.pol.entity.PolPollution;
import com.edemirkirkan.airqualityapi.pol.enums.EnumPolPollutionErrorMessage;
import com.edemirkirkan.airqualityapi.pol.dto.PolPollutionResponseDto;
import com.edemirkirkan.airqualityapi.pol.service.entityservice.PolPollutionEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PolPollutionService {
    private final PolPollutionEntityService polPollutionEntityService;
    private final PolPollutionHelperService polPollutionHelperService;
    private final PolPollutionConverter polPollutionConverter;
    private final CtyCityService ctyCityService;

    public PolPollutionResponseDto getPollutionData(String city, String startDateStr, String endDateStr) {
        Date startDate = DateUtil.atStartOfDay(DateUtil.stringToDate(startDateStr));
        Date endDate = DateUtil.atEndOfDay(DateUtil.stringToDate(endDateStr));
        polPollutionHelperService.validateApiCutoffDate(startDate, endDate);


        CtyCity ctyCity = polPollutionHelperService.checkDatabaseIfCityExists(city);
        // if database does not contain city, then query OpenWeather Geocoding API
        if (ctyCity != null) {
            polPollutionHelperService.logAction(city, EnumLogInfoEntityType.City,
                    EnumLogInfoActionType.retrieved, EnumLogInfoDataSourceType.database, null);
        }
        else {
            polPollutionHelperService. logMsg("Database does not contain a city named '"
                    + city + "' Now, retrieving...");
            CtyCityDto ctyCityDto = polPollutionHelperService.queryApiForCity(city);
            polPollutionHelperService.logAction(city, EnumLogInfoEntityType.City, EnumLogInfoActionType.retrieved
                    , EnumLogInfoDataSourceType.OpenWeatherApi, null);
            ctyCity = ctyCityService.save(ctyCityDto);
            polPollutionHelperService.logAction(city, EnumLogInfoEntityType.City, EnumLogInfoActionType.saved,
                    EnumLogInfoDataSourceType.database, null);
        }


        // given period of dates by user
        List<Date> daysBetween = DateUtil.getDatesInRange(startDate, endDate);
        // response list that is later converted to response dto and to be showed to user
        List<PolPollution> polPollutionList = new ArrayList<>();
        // indices of entries that is already existing in db
        List<Integer> foundIndices = new ArrayList<>();
        List<String> missingDates = new ArrayList<>();

        for (int i = 0, n = daysBetween.size(); i < n; i++) {
            String currentDate = DateUtil.dateToString(daysBetween.get(i));
            PolPollution polPollution = polPollutionEntityService.findByDateAndCityName(
                    currentDate, city);
            // if database does contain the pollutant information, add it to the response list
            // and also save its index for using in the later calculation regarding which period of
            // dates are not in database, and thus, should be queried from OpenWeather Geocoding API
            if (polPollution == null) {
                missingDates.add(currentDate);
            }
            else {
                foundIndices.add(i);
                polPollutionList.add(polPollution);
                polPollutionHelperService.logAction(city, EnumLogInfoEntityType.PollutantData,
                        EnumLogInfoActionType.retrieved, EnumLogInfoDataSourceType.database, currentDate);
            }
        }

        if (!missingDates.isEmpty()) {
            polPollutionHelperService.logMsg("Database does not contain a pollutant data with a city named '"
                    + city + "' for following dates " + missingDates + ". Now, retrieving...");
        }


        // calculate which periods of dates do not exist in db, and query the API for those periods
        List<Pair<Integer, Integer>> periodsToQuery = polPollutionHelperService.findDatePeriodsToQuery(
                daysBetween, foundIndices);
        polPollutionList.addAll(polPollutionHelperService.queryTheApiForPeriodsNotInDb(
                ctyCity, daysBetween, periodsToQuery));


        polPollutionHelperService.sortByDate(polPollutionList);

        return polPollutionConverter.convertToPolPollutionResponseDto(polPollutionList);
    }

    public void deletePollutionData(String city, String date) {
        PolPollution polPollution = polPollutionEntityService.findByDateAndCityName(date, city);
        if (polPollution == null) {
            throw new BusinessException(EnumPolPollutionErrorMessage.ENTRY_CANNOT_FOUND);
        }

        polPollutionEntityService.delete(polPollution);
    }
}
