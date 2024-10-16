package com.tpv.auth.infrastructure.entities.clients;

import com.tpv.auth.infrastructure.entities.clients.converters.AuthorizationGrantTypeSetConverter;
import com.tpv.auth.infrastructure.entities.clients.converters.ClientAuthenticationMethodSetConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client")
public class ClientEntity {
	@Id
	private UUID id;
	private String clientId;
	private String clientSecret;
	private String clientName;
	// @Column(length = 1000)
	@Convert(converter = ClientAuthenticationMethodSetConverter.class)
	private Set<ClientAuthenticationMethod> authenticationMethods;
	// @Column(length = 1000)
	@Convert(converter = AuthorizationGrantTypeSetConverter.class)
	private Set<AuthorizationGrantType> authorizationGrantTypes;
	// @Column(length = 1000)
	private String redirectUris;
	// @Column(length = 1000)
	private String scopes;
	// @Column(length = 2000)
	private String clientSettings;
	// @Column(length = 2000)
	private boolean requireProofKey;

	public static RegisteredClient toRegisteredClient(ClientEntity clientEntity) {
		final Set<String> redirectUriSet = Arrays.stream(clientEntity.getRedirectUris().split(",")).map(String::trim)
				.collect(Collectors.toSet());
		final Set<String> scopesSet = Arrays.stream(clientEntity.getScopes().split(",")).map(String::trim)
				.collect(Collectors.toSet());
		final RegisteredClient.Builder builder = RegisteredClient.withId(clientEntity.getClientId())
				.clientId(clientEntity.getClientId()).clientSecret(clientEntity.getClientSecret())
				.clientIdIssuedAt(new Date().toInstant())
				.clientAuthenticationMethods(am -> am.addAll(clientEntity.getAuthenticationMethods()))
				.authorizationGrantTypes(agt -> agt.addAll(clientEntity.getAuthorizationGrantTypes()))
				.redirectUris(ru -> ru.addAll(redirectUriSet)).scopes(sc -> sc.addAll(scopesSet))
				.clientSettings(ClientSettings.builder().requireProofKey(clientEntity.isRequireProofKey()).build());
		return builder.build();
	}
}
