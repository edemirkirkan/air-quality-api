package com.edemirkirkan.airqualityapi.usr.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class UsrUserSaveRequestDto {
    @NotNull
    private String firstName;
    private String lastName;
    private String username;
    @Size(min = 9)
    private String password;
}
