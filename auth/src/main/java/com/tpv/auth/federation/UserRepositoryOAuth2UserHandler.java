package com.tpv.auth.federation;

import com.tpv.auth.infrastructure.entities.users.GoogleUserEntity;
import com.tpv.auth.infrastructure.repositories.GoogleUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.function.Consumer;

@RequiredArgsConstructor
@Slf4j
public final class UserRepositoryOAuth2UserHandler implements Consumer<OAuth2User> {

	private final GoogleUserRepository googleUserRepository;

	@Override
	public void accept(OAuth2User user) {
		// Capture user in a local data store on first authentication
		if (this.googleUserRepository.findByEmail(user.getName()).isEmpty()) {
			final GoogleUserEntity googleUserEntity = GoogleUserEntity.fromOauth2User(user);
			this.googleUserRepository.save(googleUserEntity);
		} else {
			log.info("bienvenido {}", user.getAttributes().get("given_name"));
		}
	}

}
