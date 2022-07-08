package com.edemirkirkan.airqualityapi.sec.auth;

import com.edemirkirkan.airqualityapi.gen.exceptions.RestResponse;
import com.edemirkirkan.airqualityapi.usr.dto.UsrUserResponseDto;
import com.edemirkirkan.airqualityapi.usr.dto.UsrUserSaveRequestDto;
import com.edemirkirkan.airqualityapi.usr.dto.UsrUserUpdateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(tags = "Authentication Controller", summary = "Register to an API",
            description = "### Requirements\n- *Username should be unique*\n- *Password should have a minimum of nine " +
                    "characters*\n\n " +
                    "### Sample Input\n\n" + "```\n" + "{\n" +
                    "  \"firstName\": \"John\",\n" +
                    "  \"lastName\": \"Doe\",\n" +
                    "  \"username\": \"John Doe\",\n" +
                    "  \"password\": \"johndoe-123\"\n" +
                    "}" + "\n```\n\n" + "### Sample Output\n\n" + "```\n" + "{\n" +
                    "  \"data\": {\n" +
                    "    \"id\": 54,\n" +
                    "    \"firstName\": \"John\",\n" +
                    "    \"lastName\": \"Doe\",\n" +
                    "    \"username\": \"John Doe\",\n" +
                    "    \"password\": \"$2a$10$1sqVx1QF5AHyLdZ4eR3BnuRK0l7aTJDucZgdQbckuq42hiKt.H/kC\"\n" +
                    "  },\n" +
                    "  \"responseDate\": \"2022-07-08T01:17:30.931+00:00\",\n" +
                    "  \"messages\": null,\n" +
                    "  \"success\": true\n" +
                    "}" + "\n```\n\n*Note: Passwords are stored in the database with the HS512 hashing algorithm*",
                    responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            UsrUserResponseDto.class)), responseCode = "200", description = "User successfully registered"),
                    @ApiResponse(content = @Content(mediaType = "application/json"),
                            responseCode = "401", description = "Not authorized for API"),
                    @ApiResponse(content = @Content(mediaType = "application/json"),
                            responseCode = "500", description = "Invalid input")},
                    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema= @Schema(
                                    implementation = UsrUserSaveRequestDto.class
                            ),
                            examples = {
                                    @ExampleObject(
                                            name = "Successful request yields sample output.",
                                            summary = "Sample Input",
                                            value = "{\n" +
                                                    "  \"firstName\": \"John\",\n" +
                                                    "  \"lastName\": \"Doe\",\n" +
                                                    "  \"username\": \"John Doe\",\n" +
                                                    "  \"password\": \"johndoe-123\"\n" +
                                                    "}"
                                    ) } ) ) )
    @PostMapping("/register")
    public ResponseEntity<RestResponse<UsrUserResponseDto>> register(@Valid @RequestBody UsrUserSaveRequestDto usrUserSaveRequestDto){

        UsrUserResponseDto userResponseDto = authenticationService.register(usrUserSaveRequestDto);

        return ResponseEntity.ok(RestResponse.of(userResponseDto));
    }

    @Operation(tags = "Authentication Controller", summary = "Login to get access token",
            description = "### Requirements\n- *Username and password should match with the database*\n\n " +
                    "*Returns JWT (Json Web Token). Check out the link: <a href='https://jwt.io'>" +
                    "https://jwt.io</a>*\n\n " +
                    "### Sample Input\n\n" + "```\n" + "{\n" +
                    "  \"username\": \"John Doe\",\n" +
                    "  \"password\": \"johndoe-123\"\n" +
                    "}" + "\n```\n\n" + "### Sample Output\n\n" + "```\n" + "{\n" +
                    "  \"data\": \"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJKb2huIERvZSIsImlhdCI6MTY1NzI0NDI3MywiZXhwIjoxNjU3MzMwNjczfQ.VWw2T5--MLKSF3CkStynnpgIABE07j0nIbgKooH9tiSvmQg3CtoQCDQaVDJPMADG4dINrjKnAkU33vsMJS4fzw\",\n" +
                    "  \"responseDate\": \"2022-07-08T01:37:53.534+00:00\",\n" +
                    "  \"messages\": null,\n" +
                    "  \"success\": true\n" +
                    "}" + "\n```\n\n<center><h3>Authorization: *Use given JWT (Json Web Token) to login and use other " +
                    "endpoints in the API*</h3></center><center><img src='https://i.ibb.co/chMrZtc/authorize.png' " +
                    "alt='authorize' border='0' width='600'></center>",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            String.class)), responseCode = "200", description = "User successfully logged in"),
                    @ApiResponse(content = @Content(mediaType = "application/json"),
                            responseCode = "401", description = "Not authorized for API"),
                    @ApiResponse(content = @Content(mediaType = "application/json"),
                            responseCode = "500", description = "Invalid input")},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema= @Schema(
                                    implementation = AuthenticationLoginRequestDto.class
                            ),
                            examples = {
                                    @ExampleObject(
                                            name = "Successful request yields sample output.",
                                            summary = "Sample Input",
                                            value = "{\n" +
                                                    "  \"username\": \"John Doe\",\n" +
                                                    "  \"password\": \"johndoe-123\"\n" +
                                                    "}"
                                    ) } ) ) )
    @PostMapping("/login")
    public ResponseEntity<RestResponse<String>> login(@RequestBody AuthenticationLoginRequestDto loginRequestDto){

        String login = authenticationService.login(loginRequestDto);

        return ResponseEntity.ok(RestResponse.of(login));
    }
}
