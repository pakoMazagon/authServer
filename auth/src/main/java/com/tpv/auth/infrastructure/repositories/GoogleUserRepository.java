package com.tpv.auth.infrastructure.repositories;

import com.tpv.auth.infrastructure.entities.users.GoogleUserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GoogleUserRepository extends CrudRepository<GoogleUserEntity, UUID> {
	Optional<GoogleUserEntity> findByEmail(String email);
}
