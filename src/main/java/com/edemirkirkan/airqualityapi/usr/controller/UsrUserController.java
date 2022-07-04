package com.edemirkirkan.airqualityapi.usr.controller;

import com.edemirkirkan.airqualityapi.gen.exceptions.RestResponse;
import com.edemirkirkan.airqualityapi.usr.dto.UsrUserResponseDto;
import com.edemirkirkan.airqualityapi.usr.dto.UsrUserUpdateRequestDto;
import com.edemirkirkan.airqualityapi.usr.service.UsrUserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsrUserController {

    private final UsrUserService usrUserService;

    @Operation(tags = "Authentication Controller", description = "Change password")
    @PutMapping
    public ResponseEntity<RestResponse<UsrUserResponseDto>> update(
            @RequestBody UsrUserUpdateRequestDto usrUserUpdateRequestDto){

        UsrUserResponseDto userResponseDto = usrUserService.changePassword(usrUserUpdateRequestDto);

        return ResponseEntity.ok(RestResponse.of(userResponseDto));
    }
}
