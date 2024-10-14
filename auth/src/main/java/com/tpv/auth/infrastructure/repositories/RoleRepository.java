package com.tpv.auth.infrastructure.repositories;

import com.tpv.auth.domain.entitities.enums.RoleName;
import com.tpv.auth.infrastructure.entities.users.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, UUID> {
	Optional<RoleEntity> findByRole(RoleName roleName);
}
