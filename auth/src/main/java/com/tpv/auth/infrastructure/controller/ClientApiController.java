package com.tpv.auth.infrastructure.controller;

import com.tpv.auth.domain.dto.MessageDTO;
import com.tpv.auth.domain.usecases.ClientUC;
import com.tpv.auth.infrastructure.dto.CreateClientDTO;
import com.tpv.auth.infrastructure.dto.MessageResponseDTO;
import com.tpv.auth.infrastructure.mappers.ClientMapper;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("")
@RestController
@AllArgsConstructor
public class ClientApiController implements ClientsApi {

	private final ClientUC clientUC;

	private final ClientMapper clientMapper;

	private final CommonMapperController commonMapper;
	@Override
	public ResponseEntity<MessageResponseDTO> clientsCreatePost(
			@Parameter(name = "CreateClientDTO", description = "", required = true) @Valid @RequestBody CreateClientDTO createClientDTO) {
		log.info("init REQUEST addCreateClient:{}", createClientDTO.toString());
		final MessageDTO messageDTO = this.clientUC.createClient(this.clientMapper.mapToClient(createClientDTO));
		log.info("end REQUEST addCreateClient");
		return ResponseEntity.status(HttpStatus.CREATED).body(this.commonMapper.mapToMessageResponseDTO(messageDTO));
	}
}
