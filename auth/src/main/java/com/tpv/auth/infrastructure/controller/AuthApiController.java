package com.tpv.auth.infrastructure.controller;

import com.tpv.auth.domain.dto.MessageDTO;
import com.tpv.auth.domain.usecases.AppUserUC;
import com.tpv.auth.domain.vo.CreateUserVO;
import com.tpv.auth.infrastructure.dto.CreateAppUserDTO;
import com.tpv.auth.infrastructure.dto.MessageResponseDTO;
import com.tpv.auth.infrastructure.mappers.AppUserMapper;
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
public class AuthApiController implements AuthApi {

	private final AppUserUC appUserUC;

	private final AppUserMapper appUserMapper;

	@Override
	public ResponseEntity<MessageResponseDTO> authCreatePost(
			@Parameter(name = "CreateAppUserDTO", description = "", required = true) @Valid @RequestBody CreateAppUserDTO createAppUserDTO) {
		log.info("init REQUEST addCreateUser:{}", createAppUserDTO.toString());
		final CreateUserVO createUserVO = this.appUserMapper.mapToCreateUserVO(createAppUserDTO);
		final MessageDTO messageDTO = this.appUserUC.createUser(createUserVO);
		log.info("end REQUEST addCreateUser");
		return ResponseEntity.status(HttpStatus.CREATED).body(this.appUserMapper.mapToMessageResponseDTO(messageDTO));
	}
}
