package com.tpv.auth.infrastructure.entities.users;

import com.tpv.auth.domain.entitities.enums.RoleName;
import com.tpv.auth.infrastructure.entities.AuditablePersistentEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;

@Table(name = "role")
@Data
@SuperBuilder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
public class RoleEntity extends AuditablePersistentEntity implements GrantedAuthority {

	@Enumerated(EnumType.STRING)
	private RoleName role;
	@Override
	public String getAuthority() {
		return this.role.name();
	}
}
