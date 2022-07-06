package com.edemirkirkan.airqualityapi.usr.converter;

import com.edemirkirkan.airqualityapi.sec.jwt.SecUserDetails;
import com.edemirkirkan.airqualityapi.usr.dto.UsrUserResponseDto;
import com.edemirkirkan.airqualityapi.usr.dto.UsrUserSaveRequestDto;
import com.edemirkirkan.airqualityapi.usr.entity.UsrUser;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class UsrUserMapper {

    public abstract UsrUserResponseDto convertToUsrUserResponseDto(UsrUser usrUser);

    public abstract UsrUser convertToUsrUser(SecUserDetails secUserDetails);

    public abstract UsrUser convertToUsrUser(UsrUserSaveRequestDto usrUserSaveRequestDto);
}