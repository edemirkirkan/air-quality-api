package com.edemirkirkan.airqualityapi.pol.controller;

import com.edemirkirkan.airqualityapi.gen.exceptions.RestResponse;
import com.edemirkirkan.airqualityapi.gen.utils.DateUtil;
import com.edemirkirkan.airqualityapi.pol.dto.PolPollutionResponseDto;
import com.edemirkirkan.airqualityapi.pol.service.PolPollutionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/pollutions")
public class PolPollutionController {
    private final PolPollutionService polPollutionService;

    @GetMapping("/{city}/{startDate}/{endDate}")
    @Operation(tags = "Pollutant Data Controller")
    public ResponseEntity<PolPollutionResponseDto> getPollutionData(
            @PathVariable String city,
            @PathVariable String startDate,
            @PathVariable String endDate) {
        PolPollutionResponseDto polPollutionResponseDto = polPollutionService.getPollutionData(city, startDate, endDate);
        return ResponseEntity.ok(polPollutionResponseDto);
    }

    @GetMapping("/last_week/{city}")
    @Operation(tags = "Pollutant Data Controller")
    public ResponseEntity<PolPollutionResponseDto> pollutionData(@PathVariable String city) {
        Pair<String, String> lastWeek = DateUtil.getLastWeek();
        PolPollutionResponseDto polPollutionResponseDto = polPollutionService.getPollutionData(city,
                lastWeek.getFirst(), lastWeek.getSecond());
        return ResponseEntity.ok(polPollutionResponseDto);
    }

    @DeleteMapping("/{city}/{date}")
    @Operation(tags = "Pollutant Data Controller")
    public ResponseEntity<RestResponse<PolPollutionResponseDto>> pollutionData(
            @PathVariable String city,
            @PathVariable String date) {
        polPollutionService.deletePollutionData(city, date);
        return ResponseEntity.ok(RestResponse.empty());
    }

}
