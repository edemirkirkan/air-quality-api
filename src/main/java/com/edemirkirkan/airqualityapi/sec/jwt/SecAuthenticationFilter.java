package com.edemirkirkan.airqualityapi.sec.jwt;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private SecTokenGenerator tokenGenerator;

    @Autowired
    private SecUserDetailsService secUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String fullToken = request.getHeader("Authorization");
        String token = "";

        if (StringUtils.hasText(fullToken)) {
            String bearer = "Bearer ";
            if (fullToken.startsWith(bearer)) {
                token = fullToken.substring(bearer.length());
            }
        }

        if (StringUtils.hasText(token)) {
            boolean isValid = tokenGenerator.validateToken(token);
            if (isValid) {
                String username = tokenGenerator.findUsernameByToken(token);
                UserDetails userDetails = secUserDetailsService.loadUserByUsername(username);
                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}