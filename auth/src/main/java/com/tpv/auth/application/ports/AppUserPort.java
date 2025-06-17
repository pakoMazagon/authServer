package com.tpv.auth.application.ports;

import com.tpv.auth.domain.entitities.AppUser;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface AppUserPort {

    UserDetails loadUserByUsername(String userName);

    AppUser upsertUser(AppUser appUser);

    AppUser findById(UUID id);
}
