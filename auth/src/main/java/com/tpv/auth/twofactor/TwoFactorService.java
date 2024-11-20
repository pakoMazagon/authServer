package com.tpv.auth.twofactor;

import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@Service
@SessionScope
@Data
public class TwoFactorService {

    private Authentication authentication;
}
