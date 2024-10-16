package com.tpv.auth.domain.entitities;

import com.tpv.auth.domain.entitities.enums.AuthGrantTypeEnum;
import com.tpv.auth.domain.entitities.enums.ClientAuthMethodEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Client {

	private UUID id;
	private String clientId;
	private String clientSecret;
	private String clientName;
	private Set<ClientAuthMethodEnum> authenticationMethods;
	private Set<AuthGrantTypeEnum> authorizationGrantTypes;
	private String redirectUris;
	private String scopes;
	private String clientSettings;
	private boolean requireProofKey;
}
