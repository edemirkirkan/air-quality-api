package com.edemirkirkan.airqualityapi.usr.controller;

import com.edemirkirkan.airqualityapi.gen.exceptions.RestResponse;
import com.edemirkirkan.airqualityapi.usr.dto.UsrUserResponseDto;
import com.edemirkirkan.airqualityapi.usr.dto.UsrUserUpdateRequestDto;
import com.edemirkirkan.airqualityapi.usr.service.UsrUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UsrUserController {

    private final UsrUserService usrUserService;

    @Operation(tags = "Authentication Controller", summary = "Change password",
            description = "### Requirements\n- *Old password should match the password stored in database*\n- *New " +
                    "passwords should match*\n- *New password should have a minimum of nine characters*\n\n " +
                    "### Sample Input\n\n" + "```\n" + "{\n" +
                    "  \"password\": \"johndoe-123\",\n" +
                    "  \"newPassword\": \"john-doe-new-123\",\n" +
                    "  \"newPasswordAgain\": \"john-doe-new-123\"\n" +
                    "}" + "\n```" + "\n\n### Sample Output\n\n ```\n" + "{\n" +
                    "  \"data\": {\n" +
                    "    \"id\": 2,\n" +
                    "    \"firstName\": \"John\",\n" +
                    "    \"lastName\": \"Doe\",\n" +
                    "    \"username\": \"John Doe\",\n" +
                    "    \"password\": \"$2a$10$Rt.GmPbCw2f/klwev31Xi.gw5x2E7KBXDzYsAgjFeQXJ.cD0bM.kG\"\n" +
                    "  },\n" +
                    "  \"responseDate\": \"2022-07-08T00:24:10.701+00:00\",\n" +
                    "  \"messages\": null,\n" +
                    "  \"success\": true\n" +
                    "}" + "\n```\n\n*Note: Passwords are stored in the database with the HS512 hashing algorithm*",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                    UsrUserResponseDto.class)), responseCode = "200", description = "Password successfully changed"),
                    @ApiResponse(content = @Content(mediaType = "application/json"),
                            responseCode = "401", description = "Not authorized for API"),
                    @ApiResponse(content = @Content(mediaType = "application/json"),
                            responseCode = "500", description = "Invalid input")},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema= @Schema(
                                    implementation = UsrUserUpdateRequestDto.class
                            ),
                            examples = {
                                    @ExampleObject(
                                            name = "Successful request yields sample output.",
                                            summary = "Sample Input",
                                            value = "{\n" +
                                                    "  \"password\": \"johndoe-123\",\n" +
                                                    "  \"newPassword\": \"john-doe-new-123\",\n" +
                                                    "  \"newPasswordAgain\": \"john-doe-new-123\"\n" +
                                                    "}"
                                    ),
                                    @ExampleObject(
                                            name = "Throws run-time business exception with the message 'New passwords " +
                                                    "are not matching!' and 'HttpStatus.INTERNAL_SERVER_ERROR' with the code 500.",
                                            summary = "New Passwords Not Matching",
                                            value = "{\n" +
                                                    "  \"password\": \"old_password\",\n" +
                                                    "  \"newPassword\": \"new_password1\",\n" +
                                                    "  \"newPasswordAgain\": \"new_password2\"\n" +
                                                    "}"
                                    ),
                                    @ExampleObject(
                                            name = "Throws run-time business exception with the message 'Passwords should " +
                                                    "have a minimum of nine characters!' and 'HttpStatus.INTERNAL_SERVER_ERROR' " +
                                                    "with the code 500.",
                                            summary = "New Password Smaller Than Nine Characters",
                                            value = "{\n" +
                                                    "  \"password\": \"old_password\",\n" +
                                                    "  \"newPassword\": \"password\",\n" +
                                                    "  \"newPasswordAgain\": \"password\"\n" +
                                                    "}"
                                    ),
                            }
                    )
            ))
    @PatchMapping("password")
    public ResponseEntity<RestResponse<UsrUserResponseDto>> changePassword(
            @RequestBody UsrUserUpdateRequestDto usrUserUpdateRequestDto){

        UsrUserResponseDto userResponseDto = usrUserService.changePassword(usrUserUpdateRequestDto);

        return ResponseEntity.ok(RestResponse.of(userResponseDto));
    }
}
