package com.tpv.auth.application.usecases;

import com.tpv.auth.application.ports.AppUserPort;
import com.tpv.auth.application.ports.RolePort;
import com.tpv.auth.domain.dto.MessageDTO;
import com.tpv.auth.domain.entitities.AppUser;
import com.tpv.auth.domain.entitities.Role;
import com.tpv.auth.domain.entitities.enums.RoleName;
import com.tpv.auth.domain.usecases.AppUserUC;
import com.tpv.auth.domain.vo.CreateUserVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class AppUserUCImpl implements AppUserUC, UserDetailsService {

	private final AppUserPort appUserPort;
	private final RolePort rolePort;
	private final PasswordEncoder passwordEncoder;
	@Override
	public MessageDTO createUser(CreateUserVO createUserVO) {

		final Set<Role> roles = new HashSet<>();
		createUserVO.roles().forEach(r -> {
			final Role role = this.rolePort.findByRole(RoleName.valueOf(r));
			roles.add(role);
		});
		final AppUser appUser = AppUser.builder().username(createUserVO.username())
				.password(this.passwordEncoder.encode(createUserVO.password())).roles(roles).build();
		this.appUserPort.upsertUser(appUser);
		return new MessageDTO("user " + appUser.getUsername() + " saved");
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.appUserPort.loadUserByUsername(username);
	}
}
