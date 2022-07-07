package com.edemirkirkan.airqualityapi.rst.service;

import com.edemirkirkan.airqualityapi.gen.exceptions.BusinessException;
import com.edemirkirkan.airqualityapi.gen.utils.DateUtil;
import com.edemirkirkan.airqualityapi.rst.dto.RestResponseGeoDto;
import com.edemirkirkan.airqualityapi.rst.dto.RestResponsePolDto;
import com.edemirkirkan.airqualityapi.rst.enums.RestErrorMessage;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.Date;

@Service
public class RestService {

    public RestResponseGeoDto geoRequest(String cityName)  {

        String url = buildUrl(
                RestConstant.GEO_PATH, "q", cityName,
                "limit", RestConstant.LIMIT,
                "appid", RestConstant.APPID);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RestResponseGeoDto[]> responseEntity = restTemplate.getForEntity(url, RestResponseGeoDto[].class);
        if (responseEntity.getBody() == null) {
            throw new BusinessException(RestErrorMessage.RESPONSE_NULL);
        }
        if (responseEntity.getBody().length == 0) {
            throw new BusinessException(RestErrorMessage.RESPONSE_EMPTY);
        }

        return responseEntity.getBody()[0];
    }

    public RestResponsePolDto polRequest(BigDecimal latitude, BigDecimal longitude, Date startDate, Date endDate) {
        Long startTime = DateUtil.dateToTimestamp(DateUtil.atStartOfDay(startDate));
        Long endTime = DateUtil.dateToTimestamp(DateUtil.atEndOfDay(endDate));

        String url = buildUrl(
                RestConstant.POL_PATH, "lat", String.valueOf(latitude),
                "lon", String.valueOf(longitude),
                "start", String.valueOf(startTime),
                "end", String.valueOf(endTime),
                "appid", RestConstant.APPID);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RestResponsePolDto> responseEntity = restTemplate.getForEntity(url, RestResponsePolDto.class);
        return responseEntity.getBody();
    }

    private String buildUrl(String path, String... params) {
        URIBuilder builder = new URIBuilder()
                .setScheme(RestConstant.SCHEME)
                .setHost(RestConstant.HOST)
                .setPath(path);

                for (int i = 0, n = params.length - 1; i < n; i += 2) {
                    builder.addParameter(params[i], params[i + 1]);
                }

        String url;
        try {
            url = builder.build().toString();
        } catch (URISyntaxException ex) {
            throw new BusinessException(RestErrorMessage.WRONG_URL_SYNTAX);
        }
        return url;
    }



}
