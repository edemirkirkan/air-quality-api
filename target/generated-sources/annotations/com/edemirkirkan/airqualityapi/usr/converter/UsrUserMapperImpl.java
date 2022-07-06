package com.edemirkirkan.airqualityapi.usr.converter;

import com.edemirkirkan.airqualityapi.sec.jwt.SecUserDetails;
import com.edemirkirkan.airqualityapi.usr.dto.UsrUserResponseDto;
import com.edemirkirkan.airqualityapi.usr.dto.UsrUserSaveRequestDto;
import com.edemirkirkan.airqualityapi.usr.entity.UsrUser;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-06T17:50:49+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.11 (AdoptOpenJDK)"
)
@Component
public class UsrUserMapperImpl extends UsrUserMapper {

    @Override
    public UsrUserResponseDto convertToUsrUserResponseDto(UsrUser usrUser) {
        if ( usrUser == null ) {
            return null;
        }

        UsrUserResponseDto usrUserResponseDto = new UsrUserResponseDto();

        usrUserResponseDto.setId( usrUser.getId() );
        usrUserResponseDto.setFirstName( usrUser.getFirstName() );
        usrUserResponseDto.setLastName( usrUser.getLastName() );
        usrUserResponseDto.setUsername( usrUser.getUsername() );
        usrUserResponseDto.setPassword( usrUser.getPassword() );

        return usrUserResponseDto;
    }

    @Override
    public UsrUser convertToUsrUser(SecUserDetails secUserDetails) {
        if ( secUserDetails == null ) {
            return null;
        }

        UsrUser usrUser = new UsrUser();

        usrUser.setId( secUserDetails.getId() );
        usrUser.setFirstName( secUserDetails.getFirstName() );
        usrUser.setLastName( secUserDetails.getLastName() );
        usrUser.setUsername( secUserDetails.getUsername() );
        usrUser.setPassword( secUserDetails.getPassword() );

        return usrUser;
    }

    @Override
    public UsrUser convertToUsrUser(UsrUserSaveRequestDto usrUserSaveRequestDto) {
        if ( usrUserSaveRequestDto == null ) {
            return null;
        }

        UsrUser usrUser = new UsrUser();

        usrUser.setFirstName( usrUserSaveRequestDto.getFirstName() );
        usrUser.setLastName( usrUserSaveRequestDto.getLastName() );
        usrUser.setUsername( usrUserSaveRequestDto.getUsername() );
        usrUser.setPassword( usrUserSaveRequestDto.getPassword() );

        return usrUser;
    }
}
