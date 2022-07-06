package com.edemirkirkan.airqualityapi.usr.controller;

import com.edemirkirkan.airqualityapi.gen.exceptions.RestResponse;
import com.edemirkirkan.airqualityapi.usr.dto.UsrUserResponseDto;
import com.edemirkirkan.airqualityapi.usr.dto.UsrUserUpdateRequestDto;
import com.edemirkirkan.airqualityapi.usr.service.UsrUserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UsrUserController {

    private final UsrUserService usrUserService;

    @Operation(tags = "Authentication Controller", description = "Change password")
    @PatchMapping("password")
    public ResponseEntity<RestResponse<UsrUserResponseDto>> update(
            @RequestBody UsrUserUpdateRequestDto usrUserUpdateRequestDto){

        UsrUserResponseDto userResponseDto = usrUserService.changePassword(usrUserUpdateRequestDto);

        return ResponseEntity.ok(RestResponse.of(userResponseDto));
    }
}
