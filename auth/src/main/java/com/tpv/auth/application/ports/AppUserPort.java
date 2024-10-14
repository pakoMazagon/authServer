package com.tpv.auth.application.ports;

import com.tpv.auth.domain.entitities.AppUser;
import org.springframework.security.core.userdetails.UserDetails;

public interface AppUserPort {

	UserDetails loadUserByUsername(String userName);
	AppUser upsertUser(AppUser appUser);
}
