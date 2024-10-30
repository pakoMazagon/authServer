package com.tpv.auth.infrastructure.mappers;

import com.tpv.auth.domain.entitities.GoogleUser;
import com.tpv.auth.infrastructure.entities.users.GoogleUserEntity;
import org.mapstruct.*;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface GoogleUserMapper {
	GoogleUser mapToAppUser(GoogleUserEntity GoogleUserEntity);

	GoogleUserEntity mapToAppUserEntity(GoogleUser GoogleUser);
}
