package com.edemirkirkan.airqualityapi.usr.service;

import com.edemirkirkan.airqualityapi.gen.exceptions.BusinessException;
import com.edemirkirkan.airqualityapi.gen.exceptions.ErrorMessage;
import com.edemirkirkan.airqualityapi.gen.sec.jwt.SecUserDetails;
import com.edemirkirkan.airqualityapi.gen.sec.jwt.SecUserDetailsService;
import com.edemirkirkan.airqualityapi.usr.converter.UsrUserMapper;
import com.edemirkirkan.airqualityapi.usr.dto.UsrUserResponseDto;
import com.edemirkirkan.airqualityapi.usr.dto.UsrUserSaveRequestDto;
import com.edemirkirkan.airqualityapi.usr.dto.UsrUserUpdateRequestDto;
import com.edemirkirkan.airqualityapi.usr.entity.UsrUser;
import com.edemirkirkan.airqualityapi.usr.enums.UsrErrorMessage;
import com.edemirkirkan.airqualityapi.usr.service.entityservice.UsrUserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UsrUserService {

    private final UsrUserEntityService usrUserEntityService;
    private final PasswordEncoder passwordEncoder;
    private final SecUserDetailsService secUserDetailsService;
    private final UsrUserMapper usrUserMapper;


    public UsrUserResponseDto save(UsrUserSaveRequestDto usrUserSaveRequestDto) {

        if (usrUserSaveRequestDto == null){
            throw new BusinessException(ErrorMessage.PARAMETER_CANNOT_BE_NULL);
        }

        UsrUser usrUser = usrUserMapper.convertToUsrUser(usrUserSaveRequestDto);

        String password = passwordEncoder.encode(usrUser.getPassword());
        usrUser.setPassword(password);
        try {
            usrUser = usrUserEntityService.save(usrUser);
        } catch (DataIntegrityViolationException ex) {
            throw new BusinessException(UsrErrorMessage.DUPLICATE_USERNAME);
        }


        UsrUserResponseDto usrUserResponseDto = usrUserMapper.convertToUsrUserResponseDto(usrUser);

        return usrUserResponseDto;
    }

    public UsrUserResponseDto changePassword(UsrUserUpdateRequestDto usrUserUpdateRequestDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        SecUserDetails userDetails = (SecUserDetails) secUserDetailsService.loadUserByUsername(((SecUserDetails) authentication.getPrincipal()).getUsername());

        if (!Objects.equals(usrUserUpdateRequestDto.getNewPassword(), usrUserUpdateRequestDto.getNewPasswordAgain())) {
            throw new BusinessException(UsrErrorMessage.PASSWORDS_NOT_MATCHING);
        }

        if (!passwordEncoder.matches(usrUserUpdateRequestDto.getPassword(), userDetails.getPassword())) {
            throw new BusinessException(UsrErrorMessage.WRONG_PASSWORD);
        }

        UsrUser usrUser = usrUserMapper.convertToUsrUser(userDetails);

        String newPassword = passwordEncoder.encode(usrUserUpdateRequestDto.getNewPassword());
        usrUser.setPassword(newPassword);

        try {
            usrUser = usrUserEntityService.save(usrUser);
        } catch (DataIntegrityViolationException ex) {
            throw new BusinessException(UsrErrorMessage.PASSWORD_LENGTH);
        }

        UsrUserResponseDto usrUserResponseDto = usrUserMapper.convertToUsrUserResponseDto(usrUser);

        return usrUserResponseDto;
    }
}
