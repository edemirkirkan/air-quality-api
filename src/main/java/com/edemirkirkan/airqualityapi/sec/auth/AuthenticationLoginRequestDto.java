package com.edemirkirkan.airqualityapi.sec.auth;

import lombok.Data;

@Data
public class AuthenticationLoginRequestDto {
    private String username;
    private String password;
}
