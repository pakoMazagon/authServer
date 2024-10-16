package com.tpv.auth.infrastructure.mappers;

import com.tpv.auth.domain.entitities.Client;
import com.tpv.auth.domain.entitities.enums.AuthGrantTypeEnum;
import com.tpv.auth.domain.entitities.enums.ClientAuthMethodEnum;
import com.tpv.auth.infrastructure.dto.CreateClientDTO;
import com.tpv.auth.infrastructure.entities.clients.ClientEntity;
import org.mapstruct.*;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ClientMapper {

	@Mapping(source = "authenticationMethods", target = "authenticationMethods", qualifiedByName = "mapAuthMethodsToEnum")
	@Mapping(source = "authorizationGrantTypes", target = "authorizationGrantTypes", qualifiedByName = "mapAuthGrantTypesToEnum")
	Client mapToClient(ClientEntity clientEntity);

	@Mapping(source = "authenticationMethods", target = "authenticationMethods", qualifiedByName = "mapEnumToAuthMethods")
	@Mapping(source = "authorizationGrantTypes", target = "authorizationGrantTypes", qualifiedByName = "mapEnumToAuthGrantTypes")
	ClientEntity mapToClientEntity(Client client);

	@Mapping(source = "authorizationGrantTypes", target = "authorizationGrantTypes", qualifiedByName = "mapAuthGrantTypesToEnum")
	@Mapping(source = "clientSettings", target = "clientSettings", qualifiedByName = "mapClientSettingsToString")
	@Mapping(target = "redirectUris", expression = "java(String.join(\",\", registeredClient.getRedirectUris()))")
	@Mapping(target = "scopes", expression = "java(String.join(\",\", registeredClient.getScopes()))")
	Client mapRegisteredToClient(RegisteredClient registeredClient);

	@Named("mapAuthMethodsToEnum")
	default Set<ClientAuthMethodEnum> mapAuthMethodsToEnum(Set<ClientAuthenticationMethod> methods) {
		return methods.stream().map(method -> {
			switch (method.getValue()) {
				case "client_secret_basic" :
					return ClientAuthMethodEnum.CLIENT_SECRET_BASIC;
				case "client_secret_post" :
					return ClientAuthMethodEnum.CLIENT_SECRET_POST;
				case "client_secret_jwt" :
					return ClientAuthMethodEnum.CLIENT_SECRET_JWT;
				case "private_key_jwt" :
					return ClientAuthMethodEnum.PRIVATE_KEY_JWT;
				case "tls_client_auth" :
					return ClientAuthMethodEnum.TLS_CLIENT_AUTH;
				case "self_signed_tls_client_auth" :
					return ClientAuthMethodEnum.SELF_SIGNED_TLS_CLIENT_AUTH;
				case "none" :
					return ClientAuthMethodEnum.NONE;
				default :
					throw new IllegalArgumentException("Unknown method: " + method.getValue());
			}
		}).collect(Collectors.toSet());
	}

	@Named("mapEnumToAuthMethods")
	default Set<ClientAuthenticationMethod> mapEnumToAuthMethods(Set<ClientAuthMethodEnum> methods) {
		return methods.stream().map(method -> {
			switch (method) {
				case CLIENT_SECRET_BASIC :
					return ClientAuthenticationMethod.CLIENT_SECRET_BASIC;
				case CLIENT_SECRET_POST :
					return ClientAuthenticationMethod.CLIENT_SECRET_POST;
				case CLIENT_SECRET_JWT :
					return ClientAuthenticationMethod.CLIENT_SECRET_JWT;
				case PRIVATE_KEY_JWT :
					return ClientAuthenticationMethod.PRIVATE_KEY_JWT;
				case TLS_CLIENT_AUTH :
					return ClientAuthenticationMethod.TLS_CLIENT_AUTH;
				case SELF_SIGNED_TLS_CLIENT_AUTH :
					return ClientAuthenticationMethod.SELF_SIGNED_TLS_CLIENT_AUTH;
				case NONE :
					return ClientAuthenticationMethod.NONE;
				default :
					throw new IllegalArgumentException("Unknown enum: " + method);
			}
		}).collect(Collectors.toSet());
	}

	@Named("mapAuthGrantTypesToEnum")
	default Set<AuthGrantTypeEnum> mapAuthGrantTypesToEnum(Set<AuthorizationGrantType> grantTypes) {
		return grantTypes.stream().map(grantType -> {
			switch (grantType.getValue()) {
				case "authorization_code" :
					return AuthGrantTypeEnum.AUTHORIZATION_CODE;
				case "refresh_token" :
					return AuthGrantTypeEnum.REFRESH_TOKEN;
				case "client_credentials" :
					return AuthGrantTypeEnum.CLIENT_CREDENTIALS;
				case "password" :
					return AuthGrantTypeEnum.PASSWORD;
				default :
					throw new IllegalArgumentException("Unknown grant type: " + grantType);
			}
		}).collect(Collectors.toSet());
	}

	@Named("mapEnumToAuthGrantTypes")
	default Set<AuthorizationGrantType> mapEnumToAuthGrantTypes(Set<AuthGrantTypeEnum> grantTypeEnums) {
		return grantTypeEnums.stream().map(grantTypeEnum -> {
			switch (grantTypeEnum) {
				case AUTHORIZATION_CODE :
					return new AuthorizationGrantType("authorization_code");
				case REFRESH_TOKEN :
					return new AuthorizationGrantType("refresh_token");
				case CLIENT_CREDENTIALS :
					return new AuthorizationGrantType("client_credentials");
				case PASSWORD :
					return new AuthorizationGrantType("password");
				default :
					throw new IllegalArgumentException("Unknown grant type enum: " + grantTypeEnum);
			}
		}).collect(Collectors.toSet());
	}

	@Named("mapClientSettingsToString")
	default String mapClientSettingsToString(ClientSettings clientSettings) {
		return clientSettings != null ? clientSettings.toString() : null;
	}

	@Mapping(target = "redirectUris", expression = "java(String.join(\",\", createClientDTO.getRedirectUris()))")
	@Mapping(target = "scopes", expression = "java(String.join(\",\", createClientDTO.getScopes()))")
	Client mapToClient(CreateClientDTO createClientDTO);

}
