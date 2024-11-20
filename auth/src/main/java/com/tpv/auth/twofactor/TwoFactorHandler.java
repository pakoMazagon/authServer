package com.tpv.auth.twofactor;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import java.io.IOException;
import java.util.Random;

@Slf4j
public class TwoFactorHandler implements AuthenticationSuccessHandler {

    private final SecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();

    private final Authentication auth_token = new AnonymousAuthenticationToken(
            "anonymous", "anonymous", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS", "ROLE_TWO_F")
    );

    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final TwoFactorService twoFactorService;

    public TwoFactorHandler(TwoFactorService twoFactorService) {
        final SimpleUrlAuthenticationSuccessHandler authenticationSuccessHandler =
                new SimpleUrlAuthenticationSuccessHandler("/twofactor");//nombre de la plantilla
        authenticationSuccessHandler.setAlwaysUseDefaultTargetUrl(true);
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.twoFactorService = twoFactorService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        final String randomCode = generateNumberString();
        log.info("randomCode for user{} is: {}", authentication.getPrincipal(), randomCode);
        final UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) authentication;
        authToken.setDetails(randomCode);
        this.twoFactorService.sendTwilioCode(randomCode);
        this.twoFactorService.setAuthentication(authentication);
        this.setAuthentication(request, response);
        this.authenticationSuccessHandler.onAuthenticationSuccess(request, response, this.auth_token);
    }

    private void setAuthentication(HttpServletRequest request, HttpServletResponse response) {
        final SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(this.auth_token);
        SecurityContextHolder.setContext(securityContext);
        this.securityContextRepository.saveContext(securityContext, request, response);
    }

    public static String generateNumberString() {
        final Random random = new Random();
        final int number = random.nextInt(101); // Genera un número entre 0 y 100 (incluidos)
        return String.format("%02d", number); // Formatea a dos dígitos
    }
}
