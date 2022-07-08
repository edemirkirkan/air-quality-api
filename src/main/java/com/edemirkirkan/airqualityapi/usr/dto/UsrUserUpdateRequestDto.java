package com.edemirkirkan.airqualityapi.usr.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsrUserUpdateRequestDto {
    private String password;
    private String newPassword;
    private String newPasswordAgain;
}
