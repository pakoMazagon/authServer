package com.tpv.auth.infrastructure.entities;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Data
@SuperBuilder
@NoArgsConstructor(force = true)
public abstract class AuditablePersistentEntity implements Serializable {

	private static final long serialVersionUID = 1;

	@Id
	// @jakarta.persistence.Id
	private UUID id;

	@Version
	private int version;

	private String createdBy;

	@CreatedDate
	private LocalDateTime createdAt;

	private String modifiedBy;

	@LastModifiedDate
	private LocalDateTime modifiedAt;

}
