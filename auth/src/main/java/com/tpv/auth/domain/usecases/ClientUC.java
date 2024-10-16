package com.tpv.auth.domain.usecases;

import com.tpv.auth.domain.dto.MessageDTO;
import com.tpv.auth.domain.entitities.Client;

public interface ClientUC {
	MessageDTO createClient(Client client);
}
