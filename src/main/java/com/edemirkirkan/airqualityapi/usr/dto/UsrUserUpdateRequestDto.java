package com.edemirkirkan.airqualityapi.usr.dto;

import lombok.Data;

@Data
public class UsrUserUpdateRequestDto {
    private String password;
    private String newPassword;
    private String newPasswordAgain;
}
