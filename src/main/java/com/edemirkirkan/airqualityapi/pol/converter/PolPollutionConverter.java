package com.edemirkirkan.airqualityapi.pol.converter;

import com.edemirkirkan.airqualityapi.gen.exceptions.BusinessException;
import com.edemirkirkan.airqualityapi.pol.dto.*;
import com.edemirkirkan.airqualityapi.pol.entity.PolPollution;
import com.edemirkirkan.airqualityapi.pol.enums.EnumPolPollutionQualityType;
import com.edemirkirkan.airqualityapi.pol.enums.PolPollutionErrorMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PolPollutionConverter {

    public PolPollutionResponseDto convertToPolPollutionResponseDto(List<PolPollution> polPollutionList) {
        PolPollutionResponseDto polPollutionResponseDto = new PolPollutionResponseDto();
        polPollutionResponseDto.setCity(polPollutionList.get(0).getCtyCity().getName());
        List<PolPollutionResultDto> results = new ArrayList<>();
        for (PolPollution polPollution : polPollutionList) {
            PolPollutionResultDto polPollutionResultDto = new PolPollutionResultDto();
            polPollutionResultDto.setDate(polPollution.getDate());
            List<Object> qualityLabels = new ArrayList<>();
            PolPollutionCoDto polPollutionCoDto = new PolPollutionCoDto();
            PolPollutionO3Dto polPollutionO3Dto = new PolPollutionO3Dto();
            PolPollutionSo2Dto polPollutionSo2Dto = new PolPollutionSo2Dto();
            polPollutionCoDto.setCO(produceCOLabel(polPollution.getCo().doubleValue()));
            polPollutionO3Dto.setO3(produceO3Label(polPollution.getO3().doubleValue()));
            polPollutionSo2Dto.setSO2(produceSO2Label(polPollution.getSo2().doubleValue()));
            qualityLabels.add(polPollutionCoDto);
            qualityLabels.add(polPollutionO3Dto);
            qualityLabels.add(polPollutionSo2Dto);
            polPollutionResultDto.setCategories(qualityLabels);
            results.add(polPollutionResultDto);
        }
        polPollutionResponseDto.setResults(results);
        return polPollutionResponseDto;
    }

    private EnumPolPollutionQualityType produceCOLabel(Double avg) {
        if (0 <= avg && avg <= 50)
            return EnumPolPollutionQualityType.Good;
        if (50 < avg && avg <= 100)
            return EnumPolPollutionQualityType.Satisfactory;
        if (100 < avg && avg <= 150)
            return EnumPolPollutionQualityType.Moderate;
        if (150 < avg && avg <= 200)
            return EnumPolPollutionQualityType.Poor;
        if (200 < avg && avg <= 300)
            return EnumPolPollutionQualityType.Severe;
        if (300 < avg)
            return EnumPolPollutionQualityType.Hazardous;

        throw new BusinessException(PolPollutionErrorMessage.AVG_POLLUTANTS_AMOUNT_CANNOT_BE_NEGATIVE);
    }

    private EnumPolPollutionQualityType produceO3Label(Double avg) {
        if (0 <= avg && avg <= 50)
            return EnumPolPollutionQualityType.Good;
        if (50 < avg && avg <= 100)
            return EnumPolPollutionQualityType.Satisfactory;
        if (100 < avg && avg <= 168)
            return EnumPolPollutionQualityType.Moderate;
        if (168 < avg && avg <= 208)
            return EnumPolPollutionQualityType.Poor;
        if (208 < avg && avg <= 748)
            return EnumPolPollutionQualityType.Severe;
        if (748 < avg)
            return EnumPolPollutionQualityType.Hazardous;

        throw new BusinessException(PolPollutionErrorMessage.AVG_POLLUTANTS_AMOUNT_CANNOT_BE_NEGATIVE);
    }

    private EnumPolPollutionQualityType produceSO2Label(Double avg) {
        if (0 <= avg && avg <= 40)
            return EnumPolPollutionQualityType.Good;
        if (40 < avg && avg <= 80)
            return EnumPolPollutionQualityType.Satisfactory;
        if (80 < avg && avg <= 380)
            return EnumPolPollutionQualityType.Moderate;
        if (380 < avg && avg <= 800)
            return EnumPolPollutionQualityType.Poor;
        if (800 < avg && avg <= 1600)
            return EnumPolPollutionQualityType.Severe;
        if (1600 < avg)
            return EnumPolPollutionQualityType.Hazardous;

        throw new BusinessException(PolPollutionErrorMessage.AVG_POLLUTANTS_AMOUNT_CANNOT_BE_NEGATIVE);
    }


}
