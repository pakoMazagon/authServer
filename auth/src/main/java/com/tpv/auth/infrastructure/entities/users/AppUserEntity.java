package com.tpv.auth.infrastructure.entities.users;

import com.tpv.auth.infrastructure.entities.AuditablePersistentEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Table(name = "app_user")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
public class AppUserEntity extends AuditablePersistentEntity implements UserDetails {

	private String username;
	private String password;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "app_user_role", joinColumns = @JoinColumn(name = "app_user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<RoleEntity> roles;
	private boolean expired = false;
	private boolean locked = false;
	private boolean credentialsExpired = false;
	private boolean disabled = false;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return !this.expired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !this.locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !this.credentialsExpired;
	}

	@Override
	public boolean isEnabled() {
		return !this.disabled;
	}
}
