package com.tpv.auth.application.ports;

import com.tpv.auth.domain.entitities.Client;

public interface ClientPort {
	Client findClientById(String id);
	Client findClientByClientId(String clientId);

	Client saveClient(Client client);
}
