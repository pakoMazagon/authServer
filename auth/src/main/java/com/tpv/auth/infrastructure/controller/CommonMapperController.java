package com.tpv.auth.infrastructure.controller;

import com.tpv.auth.domain.dto.MessageDTO;
import com.tpv.auth.infrastructure.dto.MessageResponseDTO;
import org.mapstruct.*;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CommonMapperController {
	MessageResponseDTO mapToMessageResponseDTO(MessageDTO messageDTO);
}
