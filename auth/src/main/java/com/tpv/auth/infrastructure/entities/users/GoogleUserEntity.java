package com.tpv.auth.infrastructure.entities.users;

import com.tpv.auth.infrastructure.entities.AuditablePersistentEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "google_user")
@Data
@SuperBuilder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
public class GoogleUserEntity extends AuditablePersistentEntity {

	private String email;
	private String name;
	private String givenName;
	private String familyName;
	private String pictureUrl;

	public static GoogleUserEntity fromOauth2User(OAuth2User user) {
		final GoogleUserEntity googleUser = GoogleUserEntity.builder().email(user.getName())
				.name(user.getAttributes().get("name").toString())
				.givenName(user.getAttributes().get("given_name").toString())
				.familyName(user.getAttributes().get("family_name").toString())
				.pictureUrl(user.getAttributes().get("picture").toString()).id(UUID.randomUUID())
				.createdAt(LocalDateTime.now()).build();
		return googleUser;
	}

	@Override
	public String toString() {
		return "GoogleUser{" + " email='" + this.email + '\'' + ", name='" + this.name + '\'' + ", givenName='"
				+ this.givenName + '\'' + ", familyName='" + this.familyName + '\'' + ", pictureUrl='" + this.pictureUrl
				+ '\'' + '}';
	}

}
