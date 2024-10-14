package com.tpv.auth.domain.entitities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@SuperBuilder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class AppUser extends AuditablePersistence {
	public String username;
	private String password;
	private Set<Role> roles;
	private final boolean expired = false;
	private final boolean locked = false;
	private final boolean credentialsExpired = false;
	private final boolean disabled = false;
}
