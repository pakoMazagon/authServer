package com.tpv.auth.domain.entitities;

import com.tpv.auth.domain.entitities.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Role extends AuditablePersistence {

	private RoleName role;
}
