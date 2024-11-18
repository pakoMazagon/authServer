package com.tpv.auth.infrastructure.entities.authconsent.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "authorizationConsent")
@IdClass(AuthorizationConsentEntity.AuthorizationConsentId.class)
public class AuthorizationConsentEntity {
	@Id
	private String registeredClientId;
	@Id
	private String principalName;

	private String authorities;

	public String getRegisteredClientId() {
		return this.registeredClientId;
	}

	public void setRegisteredClientId(String registeredClientId) {
		this.registeredClientId = registeredClientId;
	}

	public String getPrincipalName() {
		return this.principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	public String getAuthorities() {
		return this.authorities;
	}

	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}

	public static class AuthorizationConsentId implements Serializable {
		private String registeredClientId;
		private String principalName;

		public String getRegisteredClientId() {
			return this.registeredClientId;
		}

		public void setRegisteredClientId(String registeredClientId) {
			this.registeredClientId = registeredClientId;
		}

		public String getPrincipalName() {
			return this.principalName;
		}

		public void setPrincipalName(String principalName) {
			this.principalName = principalName;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || this.getClass() != o.getClass())
				return false;
			final AuthorizationConsentId that = (AuthorizationConsentId) o;
			return this.registeredClientId.equals(that.registeredClientId)
					&& this.principalName.equals(that.principalName);
		}

		@Override
		public int hashCode() {
			return Objects.hash(this.registeredClientId, this.principalName);
		}
	}
}
