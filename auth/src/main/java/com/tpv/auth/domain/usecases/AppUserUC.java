package com.tpv.auth.domain.usecases;

import com.tpv.auth.domain.dto.MessageDTO;
import com.tpv.auth.domain.vo.CreateUserVO;

public interface AppUserUC {
	MessageDTO createUser(CreateUserVO createUserVO);
}
