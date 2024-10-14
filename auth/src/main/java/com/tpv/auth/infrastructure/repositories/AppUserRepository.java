package com.tpv.auth.infrastructure.repositories;

import com.tpv.auth.infrastructure.entities.users.AppUserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppUserRepository extends CrudRepository<AppUserEntity, UUID> {

	Optional<AppUserEntity> findByUsername(String username);
}
