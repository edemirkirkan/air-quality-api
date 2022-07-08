package com.edemirkirkan.airqualityapi;

import com.edemirkirkan.airqualityapi.gen.exceptions.RestResponse;
import com.edemirkirkan.airqualityapi.pol.dto.PolPollutionResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

public class BaseTest {

    protected ObjectMapper objectMapper;

    protected boolean isSuccess(MvcResult result) throws com.fasterxml.jackson.core.JsonProcessingException, UnsupportedEncodingException {

        RestResponse restResponse = getRestResponse(result);

        return restResponse.isSuccess();
    }

    protected RestResponse getRestResponse(MvcResult result) throws JsonProcessingException, UnsupportedEncodingException {
        RestResponse restResponse = objectMapper.readValue(result.getResponse().getContentAsString(), RestResponse.class);
        return restResponse;
    }

    protected PolPollutionResponseDto getRestResponsePol(MvcResult result) throws
            JsonProcessingException, UnsupportedEncodingException {
        PolPollutionResponseDto polPollutionResponseDto = objectMapper.readValue(result.getResponse()
                .getContentAsString(), PolPollutionResponseDto.class);
        return polPollutionResponseDto;
    }

    protected boolean isSuccessPol(MvcResult result, int expectedDayResult) throws com.fasterxml.jackson.core.JsonProcessingException, UnsupportedEncodingException {

        PolPollutionResponseDto pollutionResponseDto = getRestResponsePol(result);

        return pollutionResponseDto.getResults().size() == expectedDayResult;
    }
}
