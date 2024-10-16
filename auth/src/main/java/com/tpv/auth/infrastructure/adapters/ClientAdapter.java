package com.tpv.auth.infrastructure.adapters;

import com.tpv.auth.application.ports.ClientPort;
import com.tpv.auth.domain.entitities.Client;
import com.tpv.auth.infrastructure.entities.clients.ClientEntity;
import com.tpv.auth.infrastructure.mappers.ClientMapper;
import com.tpv.auth.infrastructure.repositories.clients.ClientRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class ClientAdapter implements ClientPort, RegisteredClientRepository {

	private final ClientRepository clientRepository;
	private final ClientMapper clientMapper;
	private final PasswordEncoder passwordEncoder;

	@Override
	public Client findClientById(String id) {
		return this.clientMapper.mapRegisteredToClient(this.findById(id));
	}

	@Override
	public Client findClientByClientId(String clientId) {
		return this.clientMapper.mapRegisteredToClient(this.findByClientId(clientId));
	}

	@Override
	public Client saveClient(Client client) {
		client.setClientSecret(this.passwordEncoder.encode(client.getClientSecret()));
		ClientEntity clientEntity = this.clientMapper.mapToClientEntity(client);
		clientEntity = this.clientRepository.save(clientEntity);
		return this.clientMapper.mapToClient(clientEntity);
	}

	@Override
	public RegisteredClient findById(String id) {
		final ClientEntity clientEntity = this.clientRepository.findByClientId(id)
				.orElseThrow(() -> new RuntimeException("client not found"));
		return ClientEntity.toRegisteredClient(clientEntity);
	}

	@Override
	public RegisteredClient findByClientId(String clientId) {
		final Optional<ClientEntity> clientEntity = this.clientRepository.findByClientId(clientId);
		if (clientEntity.isEmpty()) {
			new RuntimeException("client not found");
		}
		return ClientEntity.toRegisteredClient(clientEntity.get());
	}

	@Override
	public void save(RegisteredClient registeredClient) {

	}

}
