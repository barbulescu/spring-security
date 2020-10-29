package com.barbulescu.springsecurity.web;

import com.barbulescu.springsecurity.db.EncryptionAlgorithm;
import com.barbulescu.springsecurity.db.UserRepository;
import lombok.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Value
public class AuthenticationProviderService implements AuthenticationProvider {

    UserRepository userDetailsService;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    SCryptPasswordEncoder sCryptPasswordEncoder;

    @Override
    public Authentication authenticate(
            Authentication authentication)
            throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication
                .getCredentials()
                .toString();

        return userDetailsService.findUserByUsername(username)
                .map(CustomUserDetails::new)
                .map(user -> checkPassword(user, password))
                .orElseThrow(() -> new BadCredentialsException("Bad credentials"));
    }

    private Authentication checkPassword(CustomUserDetails user, String password) {
        EncryptionAlgorithm algorithm = user.getAlgorithm();
        switch (algorithm) {
            case BCRYPT:
                return checkPassword(user, password, bCryptPasswordEncoder);
            case SCRYPT:
                return checkPassword(user, password, sCryptPasswordEncoder);
            default:
                throw new IllegalArgumentException("Unsupported algorithm: " + algorithm);
        }
    }

    private Authentication checkPassword(CustomUserDetails user, String rawPassword, PasswordEncoder encoder) {
        if (!encoder.matches(rawPassword, user.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }

        return new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
