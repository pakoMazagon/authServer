package com.tpv.auth.infrastructure.mappers;

import com.tpv.auth.domain.dto.MessageDTO;
import com.tpv.auth.domain.entitities.AppUser;
import com.tpv.auth.domain.entitities.Role;
import com.tpv.auth.domain.vo.CreateUserVO;
import com.tpv.auth.infrastructure.dto.CreateAppUserDTO;
import com.tpv.auth.infrastructure.dto.MessageResponseDTO;
import com.tpv.auth.infrastructure.entities.users.AppUserEntity;
import com.tpv.auth.infrastructure.entities.users.RoleEntity;
import org.mapstruct.*;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AppUserMapper {
	AppUser mapToAppUser(AppUserEntity appUserEntity);

	@InheritInverseConfiguration
	@Mapping(source = "username", target = "username")
	@Mapping(source = "password", target = "password")
	AppUserEntity mapToAppUserEntity(AppUser appUser);

	Role mapToRole(RoleEntity RoleEntity);
	RoleEntity mapToRoleEntity(Role Role);

	CreateUserVO mapToCreateUserVO(CreateAppUserDTO createAppUserDtoDTO);

	MessageResponseDTO mapToMessageResponseDTO(MessageDTO messageDTO);
}
