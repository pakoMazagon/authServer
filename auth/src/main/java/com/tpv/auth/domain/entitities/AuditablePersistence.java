package com.tpv.auth.domain.entitities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@SuperBuilder
@NoArgsConstructor(force = true)
public abstract class AuditablePersistence implements Serializable {

	private static final long serialVersionUID = 1;

	private UUID id;
	private int version;
	private String createdBy;
	private LocalDateTime createdAt;
	private String modifiedBy;
	private LocalDateTime modifiedAt;
}
