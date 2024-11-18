package com.tpv.auth.infrastructure.repositories;

import com.tpv.auth.infrastructure.entities.authconsent.entities.AuthorizationConsentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorizationConsentRepository
		extends
			JpaRepository<AuthorizationConsentEntity, AuthorizationConsentEntity.AuthorizationConsentId> {
	Optional<AuthorizationConsentEntity> findByRegisteredClientIdAndPrincipalName(String registeredClientId,
			String principalName);
	void deleteByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);
}
