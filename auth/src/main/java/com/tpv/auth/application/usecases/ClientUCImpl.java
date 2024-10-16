package com.tpv.auth.application.usecases;

import com.tpv.auth.application.ports.ClientPort;
import com.tpv.auth.domain.dto.MessageDTO;
import com.tpv.auth.domain.entitities.Client;
import com.tpv.auth.domain.usecases.ClientUC;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class ClientUCImpl implements ClientUC {

	private final ClientPort clientPort;

	@Override
	public MessageDTO createClient(Client client) {
		log.info("createClient:{}", client.toString());
		client.setId(UUID.randomUUID());
		this.clientPort.saveClient(client);
		return new MessageDTO("client " + client.getClientId() + " saved");

	}
}
