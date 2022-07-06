package com.edemirkirkan.airqualityapi.sec.jwt;

import com.edemirkirkan.airqualityapi.usr.entity.UsrUser;
import com.edemirkirkan.airqualityapi.usr.service.entityservice.UsrUserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UsrUserEntityService usrUserEntityService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsrUser usrUser = usrUserEntityService.findByUsername(username);
        return SecUserDetails.build(usrUser);
    }
}
