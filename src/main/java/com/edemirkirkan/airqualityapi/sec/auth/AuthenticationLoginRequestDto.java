package com.edemirkirkan.airqualityapi.sec.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationLoginRequestDto {
    private String username;
    private String password;
}
