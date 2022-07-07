package com.edemirkirkan.airqualityapi.pol.controller;

import com.edemirkirkan.airqualityapi.gen.exceptions.RestResponse;
import com.edemirkirkan.airqualityapi.pol.dto.PolPollutionResponseDto;
import com.edemirkirkan.airqualityapi.pol.service.PolPollutionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/pollutions")
public class PolPollutionController {
    private final PolPollutionService polPollutionService;

    @GetMapping()
    @Operation(
            tags = "Pollutant Data Controller",
            summary = "Retrieves the pollutant data with the specified city and interval",
            description = "## Description\nThis endpoint categorize and returns the health hazards of *Monoxide(CO)*, " +
                    "*Ozone(S02)*, and *Sulphur Dioxide(O3)* substances using real historical data and the following " +
                    "*pollutants and health breakpoints* table published unanimously by governments." +
                    "\n### <center>Pollutants and Health Breakpoints</center>\n\n<center>" +
                    "<img src='https://i.ibb.co/0Y2JRRV/table.png' alt='table' border='0' height='200'>" +
                    "</center>\n\n **Sample Input**\n\n- Given dates are in the dd-MM-yyyy format and after " +
                    "27 November 2020.\n- Start date is before end date." + "\n- Given city is one of the accepted " +
                    "cities for the API.\n- Following input is valid...\n" +
                    "```\n" + "{\n   \"city\": \"Ankara\"," +
                    "\n   \"startDate\": \"12-06-2021\"," +
                    "\n   \"endDate\": \"13-06-2021\"\n}\n```\n" +
                    "**Sample Output**\n" +
                    "```\n{\n" +
                    "  \"city\": \"Ankara\",\n" +
                    "  \"results\": [\n" +
                    "    {\n" +
                    "      \"date\": \"12-06-2021\",\n" +
                    "      \"categories\": [\n" +
                    "        {\n" +
                    "          \"co\": \"Severe\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"o3\": \"Good\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"so2\": \"Good\"\n" +
                    "        }\n" +
                    "      ]\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"date\": \"13-06-2021\",\n" +
                    "      \"categories\": [\n" +
                    "        {\n" +
                    "          \"co\": \"Severe\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"o3\": \"Good\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"so2\": \"Good\"\n" +
                    "        }\n" +
                    "      ]\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}\n```\n" +
                    "**Sample Invalid Input**\n" +
                    "\n- Given dates aren't in the dd-MM-yyyy format and before 27 November 2020." +
                    "\n- Start date isn't before end date." + "\n- Given city isn't one of the accepted " +
                    "cities for the API.\n- Following input is invalid!\n" +
                    "```\n" + "{\n   \"city\": \"Istanbul\"," +
                    "\n   \"startDate\": \"24/04/2019\"," +
                    "\n   \"endDate\": \"15/04/2019\"\n}\n```\n",
            parameters = {
                    @Parameter(name = "city", in = ParameterIn.QUERY, required = true, description = "Note: API can " +
                            "only query the following cities\n- *London*\n- *Barcelona*\n- *Ankara*\n- *Tokyo*\n- *Mumbai*\n",
                            schema = @Schema(type = "string", format = "Cityname")),
                    @Parameter(name = "startDate", in = ParameterIn.QUERY, required = true, description = "Note: Take a " +
                            "glance at below restrictions and regulations before sending a request\n\n- *API can only query " +
                            "the dates since 27 November 2020*\n- *Format of the date should be of the form dd-MM-yyyy*\n- " +
                            "*If both start and end dates are sent empty, last week will be taken into consideration by " +
                            "default*", schema = @Schema(type = "string", format = "dd-MM-yyyy"), allowEmptyValue = true),
                    @Parameter(name="endDate", in = ParameterIn.QUERY, required = true, description = "Note: Take a glance " +
                            "at below restrictions and regulations before sending a request\n\n- *API can only query the " +
                            "dates since 27 November 2020*\n- *Format of the date should be of the form dd-MM-yyyy*\n- " +
                            "*If both start and end dates are sent empty, last week will be taken into consideration by " +
                            "default*", schema = @Schema(type = "string", format = "dd-MM-yyyy"), allowEmptyValue = true)},
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                    PolPollutionResponseDto.class)), responseCode = "200", description = "Data successfully received"),
                         @ApiResponse(content = @Content(mediaType = "application/json"),
                                 responseCode = "401", description = "Not authorized for API"),
                         @ApiResponse(content = @Content(mediaType = "application/json"),
                                 responseCode = "500", description = "Invalid input")} )
    public ResponseEntity<PolPollutionResponseDto> getPollutionData(String city, String startDate, String endDate) {
        PolPollutionResponseDto polPollutionResponseDto = polPollutionService.getPollutionData(city, startDate, endDate);
        return ResponseEntity.ok(polPollutionResponseDto);
    }


    @DeleteMapping()
    @Operation(tags = "Pollutant Data Controller", summary = "Deletes the pollutant data with the specified city and date",
                parameters = {
                        @Parameter(name = "city", in = ParameterIn.QUERY, required = true, description = "Note: API " +
                        "can only query the following cities\n- *London*\n- *Barcelona*\n- *Ankara*\n- *Tokyo*\n- *Mumbai*" +
                        "\n", schema = @Schema(type = "string", format = "Cityname")),
                        @Parameter(name = "date", in = ParameterIn.QUERY, required = true, schema = @Schema(type = "string",
                                format = "dd-MM-yyyy"), description = "Note: Format of the date should be of the form dd-MM-yyyy")},
                responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                    PolPollutionResponseDto.class)), responseCode = "200", description = "Data successfully deleted"),
                    @ApiResponse(content = @Content(mediaType = "application/json"),
                            responseCode = "401", description = "Not authorized for API"),
                    @ApiResponse(content = @Content(mediaType = "application/json"),
                            responseCode = "500", description = "Invalid input")})
    public ResponseEntity<RestResponse> deletePollutionData (String city, String date) {
        polPollutionService.deletePollutionData(city, date);
        return ResponseEntity.ok(RestResponse.empty());
    }

}
