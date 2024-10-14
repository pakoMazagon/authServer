package com.tpv.auth.infrastructure.mappers;

import com.tpv.auth.domain.entitities.Role;
import com.tpv.auth.infrastructure.entities.users.RoleEntity;
import org.mapstruct.*;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface RoleMapper {
	Role mapToRole(RoleEntity RoleEntity);
	RoleEntity mapToRoleEntity(Role Role);
}
