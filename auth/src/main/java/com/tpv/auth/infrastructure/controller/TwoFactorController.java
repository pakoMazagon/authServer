package com.tpv.auth.infrastructure.controller;

import com.tpv.auth.twofactor.TwoFactorService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class TwoFactorController {
    private final SecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();

    private final AuthenticationFailureHandler authenticationFailureHandler =
            new SimpleUrlAuthenticationFailureHandler("/twofactor?error");

    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final TwoFactorService twoFactorService;

    @GetMapping("/twofactor")
    public String twofactor() {
        return "twofactor";
    }

    @PostMapping("/twofactor")
    public void validateCode(@RequestParam("code") String code, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        final UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) this.twoFactorService.getAuthentication();
        if (code.equals(authToken.getDetails())) {
            this.authenticationSuccessHandler.onAuthenticationSuccess(req, res, this.getAuthentication(req, res));
        } else {
            this.authenticationFailureHandler.onAuthenticationFailure(req, res, new BadCredentialsException("invalid CODE"));
        }
    }

    private Authentication getAuthentication(HttpServletRequest req, HttpServletResponse res) {
        final Authentication authentication = this.twoFactorService.getAuthentication();
        final SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
        this.securityContextRepository.saveContext(securityContext, req, res);
        return authentication;
    }

}
