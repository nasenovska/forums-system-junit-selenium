package org.master.testing.controller;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.master.testing.configuration.JsonWebTokenConfiguration;
import org.master.testing.security.UserCredentials;
import org.master.testing.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * Api endpoints for using the authentication functionality
 *
 * @version 1.0
 * @implNote {@link UserCredentials}
 * @apiNote /api/v1/auth
 */
@RestController
@RequestMapping(value = "api/v1")
public class AuthenticationController {

    private final SecurityService securityService;
    private final JsonWebTokenConfiguration configuration;

    @Autowired
    public AuthenticationController(SecurityService securityService, JsonWebTokenConfiguration configuration) {
        this.securityService = securityService;
        this.configuration = configuration;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful login."),
            @ApiResponse(code = 403, message = "Authentication failed.")
    })
    @PostMapping(value = "/auth")
    public HttpEntity<?> authenticate(@RequestBody UserCredentials userCredentials,
                                      HttpServletResponse response) {
        String jsonWebToken = securityService.authenticate(userCredentials);
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader(configuration.getHeader(), jsonWebToken);

        return ResponseEntity.ok()
                .build();
    }
}
