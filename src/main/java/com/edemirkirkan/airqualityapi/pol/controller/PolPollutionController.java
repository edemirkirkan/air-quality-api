package com.edemirkirkan.airqualityapi.pol.controller;

import com.edemirkirkan.airqualityapi.gen.utils.DateUtil;
import com.edemirkirkan.airqualityapi.pol.dto.PolPollutionResponseDto;
import com.edemirkirkan.airqualityapi.pol.service.PolPollutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/pollutions")
public class PolPollutionController {
    private final PolPollutionService polPollutionService;

    @GetMapping("/{city}/{startDate}/{endDate}")
    public ResponseEntity<PolPollutionResponseDto> getPollutionData(
            @PathVariable String city,
            @PathVariable String startDate,
            @PathVariable String endDate) {
        PolPollutionResponseDto polPollutionResponseDto = polPollutionService.getPollutionData(city, startDate, endDate);
        return ResponseEntity.ok(polPollutionResponseDto);
    }

    @GetMapping("/last week/{city}")
    public ResponseEntity<PolPollutionResponseDto> getPollutionData(
            @PathVariable String city) {
        Calendar cal = Calendar.getInstance();
        String endDate = DateUtil.dateToString(cal.getTime());
        cal.add(Calendar.DATE, -7);
        String startDate = DateUtil.dateToString(cal.getTime());
        PolPollutionResponseDto polPollutionResponseDto = polPollutionService.getPollutionData(city, startDate, endDate);
        return ResponseEntity.ok(polPollutionResponseDto);
    }


}
