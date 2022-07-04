package com.edemirkirkan.airqualityapi.gen.sec.auth;

import com.edemirkirkan.airqualityapi.gen.sec.jwt.SecTokenGenerator;
import com.edemirkirkan.airqualityapi.gen.sec.jwt.SecUserDetails;
import com.edemirkirkan.airqualityapi.usr.dto.UsrUserResponseDto;
import com.edemirkirkan.airqualityapi.usr.dto.UsrUserSaveRequestDto;
import com.edemirkirkan.airqualityapi.usr.service.UsrUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UsrUserService usrUserService;
    private final SecTokenGenerator secTokenGenerator;

    public UsrUserResponseDto register(UsrUserSaveRequestDto usrUserSaveRequestDto) {
        return usrUserService.save(usrUserSaveRequestDto);
    }

    public String login(AuthenticationLoginRequestDto authenticationLoginRequestDto) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                authenticationLoginRequestDto.getUsername(), authenticationLoginRequestDto.getPassword()
        );

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = secTokenGenerator.generateJwtToken(authentication);

        return AuthenticationConstant.BEARER.getConstant() + token;
    }

    public static Long currentUserId(){

        SecUserDetails secUserDetails = getJwtUserDetails();

        return getJwtUserDetailsId(secUserDetails);
    }

    private static Long getJwtUserDetailsId(SecUserDetails secUserDetails) {
        Long userId = null;
        if (secUserDetails != null){
            userId = secUserDetails.getId();
        }
        return userId;
    }

    private static SecUserDetails getJwtUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        SecUserDetails jwtUserDetails = null;
        if (authentication != null && authentication.getPrincipal() instanceof SecUserDetails){
            jwtUserDetails = (SecUserDetails) authentication.getPrincipal();
        }
        return jwtUserDetails;
    }

}
