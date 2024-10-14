package com.tpv.auth.infrastructure.adapters;

import com.tpv.auth.application.ports.RolePort;
import com.tpv.auth.domain.entitities.Role;
import com.tpv.auth.domain.entitities.enums.RoleName;
import com.tpv.auth.infrastructure.mappers.RoleMapper;
import com.tpv.auth.infrastructure.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class RoleAdapter implements RolePort {

	RoleRepository roleRepository;
	RoleMapper roleMapper;
	@Override
	public Role findByRole(RoleName roleName) {
		return this.roleMapper.mapToRole(this.roleRepository.findByRole(roleName)
				.orElseThrow(() -> new RuntimeException("No exist rol with name {}")));
	}
}
