package org.master.testing.security.service.impl;

import org.master.testing.configuration.JsonWebTokenConfiguration;
import org.master.testing.security.JsonWebTokenProvider;
import org.master.testing.security.UserCredentials;
import org.master.testing.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final AuthenticationManager authenticationManager;
    private final JsonWebTokenProvider jsonWebTokenProvider;
    private final JsonWebTokenConfiguration configuration;

    @Autowired
    public SecurityServiceImpl(AuthenticationManager authenticationManager, JsonWebTokenProvider jsonWebTokenProvider, JsonWebTokenConfiguration configuration) {
        this.authenticationManager = authenticationManager;
        this.jsonWebTokenProvider = jsonWebTokenProvider;
        this.configuration = configuration;
    }

    @Override
    public String authenticate(UserCredentials userCredentials) {
        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userCredentials.getEmail(),
                    userCredentials.getPassword());
            Authentication authentication = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return String.format("%s %s",
                    configuration.getType(),
                    jsonWebTokenProvider.generateToken(authentication));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException(e.getMessage(), e);
        }
    }
}
