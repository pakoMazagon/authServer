package com.tpv.auth.infrastructure.entities.clients.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class AuthorizationGrantTypeSetConverter implements AttributeConverter<Set<AuthorizationGrantType>, String> {

	@Override
	public String convertToDatabaseColumn(Set<AuthorizationGrantType> grantTypes) {
		return grantTypes.stream().map(AuthorizationGrantType::getValue).collect(Collectors.joining(","));
	}

	@Override
	public Set<AuthorizationGrantType> convertToEntityAttribute(String dbData) {
		return Arrays.stream(dbData.split(",")).map(AuthorizationGrantType::new).collect(Collectors.toSet());
	}
}
