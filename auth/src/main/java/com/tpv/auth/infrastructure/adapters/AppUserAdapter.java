package com.tpv.auth.infrastructure.adapters;

import com.tpv.auth.application.ports.AppUserPort;
import com.tpv.auth.domain.entitities.AppUser;
import com.tpv.auth.infrastructure.entities.users.AppUserEntity;
import com.tpv.auth.infrastructure.mappers.AppUserMapper;
import com.tpv.auth.infrastructure.repositories.AppUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class AppUserAdapter implements AppUserPort {

	AppUserRepository appUserRepository;
	AppUserMapper appUserMapper;
	@Override
	public UserDetails loadUserByUsername(String userName) {
		return this.appUserRepository.findByUsername(userName)
				.orElseThrow(() -> new RuntimeException("user not found"));
	}

	@Override
	public AppUser upsertUser(AppUser appUser) {
		final Optional<AppUserEntity> appUserOpt = this.appUserRepository.findByUsername(appUser.getUsername());
		final AppUserEntity appUserEntity = this.appUserMapper.mapToAppUserEntity(appUser);
		if (appUserOpt.isEmpty()) {
			log.info("NEW User");
			appUserEntity.setId(UUID.randomUUID());
			return this.appUserMapper.mapToAppUser(this.appUserRepository.save(appUserEntity));
		}
		log.info("Modify User");
		appUserEntity.setId(appUserOpt.get().getId());
		appUserEntity.setModifiedAt(LocalDateTime.now());
		appUserEntity.setVersion(appUserOpt.get().getVersion());
		return this.appUserMapper.mapToAppUser(this.appUserRepository.save(appUserEntity));
	}
}
