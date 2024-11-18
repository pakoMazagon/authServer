package com.tpv.auth.application.usecases;

import com.tpv.auth.infrastructure.entities.authconsent.entities.AuthorizationConsentEntity;
import com.tpv.auth.infrastructure.repositories.AuthorizationConsentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthorizationConsentUCImpl implements OAuth2AuthorizationConsentService {
    private final AuthorizationConsentRepository authorizationConsentRepository;
    private final RegisteredClientRepository registeredClientRepository;

    @Override
    public void save(OAuth2AuthorizationConsent authorizationConsent) {
        Assert.notNull(authorizationConsent, "authorizationConsent cannot be null");
        this.authorizationConsentRepository.save(this.toEntity(authorizationConsent));
    }

    @Override
    public void remove(OAuth2AuthorizationConsent authorizationConsent) {
        Assert.notNull(authorizationConsent, "authorizationConsent cannot be null");
        this.authorizationConsentRepository.deleteByRegisteredClientIdAndPrincipalName(
                authorizationConsent.getRegisteredClientId(), authorizationConsent.getPrincipalName());
    }

    @Override
    public OAuth2AuthorizationConsent findById(String registeredClientId, String principalName) {
        Assert.hasText(registeredClientId, "registeredClientId cannot be empty");
        Assert.hasText(principalName, "principalName cannot be empty");
        return this.authorizationConsentRepository
                .findByRegisteredClientIdAndPrincipalName(registeredClientId, principalName).map(this::toObject)
                .orElse(null);
    }

    private OAuth2AuthorizationConsent toObject(AuthorizationConsentEntity authorizationConsent) {
        final String registeredClientId = authorizationConsent.getRegisteredClientId();
        final RegisteredClient registeredClient = this.registeredClientRepository.findById(registeredClientId);
        if (registeredClient == null) {
            throw new DataRetrievalFailureException("The RegisteredClient with id '" + registeredClientId
                    + "' was not found in the RegisteredClientRepository.");
        }

        final OAuth2AuthorizationConsent.Builder builder = OAuth2AuthorizationConsent.withId(registeredClientId,
                authorizationConsent.getPrincipalName());
        if (authorizationConsent.getAuthorities() != null) {
            for (final String authority : StringUtils.commaDelimitedListToSet(authorizationConsent.getAuthorities())) {
                builder.authority(new SimpleGrantedAuthority(authority));
            }
        }

        return builder.build();
    }

    private AuthorizationConsentEntity toEntity(OAuth2AuthorizationConsent authorizationConsent) {
        final AuthorizationConsentEntity entity = new AuthorizationConsentEntity();
        entity.setRegisteredClientId(authorizationConsent.getRegisteredClientId());
        entity.setPrincipalName(authorizationConsent.getPrincipalName());

        final Set<String> authorities = new HashSet<>();
        for (final GrantedAuthority authority : authorizationConsent.getAuthorities()) {
            authorities.add(authority.getAuthority());
        }
        entity.setAuthorities(StringUtils.collectionToCommaDelimitedString(authorities));

        return entity;
    }
}
