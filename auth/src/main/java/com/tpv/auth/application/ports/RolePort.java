package com.tpv.auth.application.ports;

import com.tpv.auth.domain.entitities.Role;
import com.tpv.auth.domain.entitities.enums.RoleName;

public interface RolePort {

	Role findByRole(RoleName roleName);
}
