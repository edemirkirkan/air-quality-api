package com.edemirkirkan.airqualityapi.usr.dto;

import lombok.Data;

@Data
public class UsrUserResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
}