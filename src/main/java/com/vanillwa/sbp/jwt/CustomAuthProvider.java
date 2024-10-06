package com.vanillwa.sbp.jwt;

import com.vanillwa.sbp.dto.CustomUserDetails;
import com.vanillwa.sbp.entity.UserEntity;
import com.vanillwa.sbp.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthProvider implements AuthenticationProvider {

    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        CustomUserDetails customUserDetails = (CustomUserDetails) customUserDetailsService
                .loadUserByUsername(username);

        UserEntity user = customUserDetails.getUserEntity();
        if (user == null) {
            throw new BadCredentialsException("NoAccount");
        }

        String dbPassword = customUserDetails.getPassword();

        if (!passwordEncoder.matches(password, dbPassword)) {
            throw new BadCredentialsException("PasswordFail");
        }

        return new UsernamePasswordAuthenticationToken(customUserDetails, null,
                customUserDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}

