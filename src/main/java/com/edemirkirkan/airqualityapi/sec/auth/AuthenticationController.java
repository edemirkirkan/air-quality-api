package com.edemirkirkan.airqualityapi.sec.auth;

import com.edemirkirkan.airqualityapi.gen.exceptions.RestResponse;
import com.edemirkirkan.airqualityapi.usr.dto.UsrUserResponseDto;
import com.edemirkirkan.airqualityapi.usr.dto.UsrUserSaveRequestDto;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(tags = "Authentication Controller", description = "Register to an API")
    @PostMapping("/register")
    public ResponseEntity<RestResponse<UsrUserResponseDto>> register(@Valid @RequestBody UsrUserSaveRequestDto usrUserSaveRequestDto){

        UsrUserResponseDto userResponseDto = authenticationService.register(usrUserSaveRequestDto);

        return ResponseEntity.ok(RestResponse.of(userResponseDto));
    }

    @Operation(tags = "Authentication Controller", description = "Login to get access token")
    @PostMapping("/login")
    public ResponseEntity<RestResponse<String>> login(@RequestBody AuthenticationLoginRequestDto loginRequestDto){

        String login = authenticationService.login(loginRequestDto);

        return ResponseEntity.ok(RestResponse.of(login));
    }
}
