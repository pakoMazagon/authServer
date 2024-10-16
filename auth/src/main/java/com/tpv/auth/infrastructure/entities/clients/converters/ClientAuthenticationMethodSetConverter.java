package com.tpv.auth.infrastructure.entities.clients.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class ClientAuthenticationMethodSetConverter
		implements
			AttributeConverter<Set<ClientAuthenticationMethod>, String> {

	@Override
	public String convertToDatabaseColumn(Set<ClientAuthenticationMethod> methods) {
		return methods.stream().map(ClientAuthenticationMethod::getValue).collect(Collectors.joining(","));
	}

	@Override
	public Set<ClientAuthenticationMethod> convertToEntityAttribute(String dbData) {
		return Arrays.stream(dbData.split(",")).map(ClientAuthenticationMethod::new).collect(Collectors.toSet());
	}
}
