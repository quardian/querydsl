package com.inho.datajpa.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;


/* JPA주요 이벤트 어노테이션
@EnableJpaAuditing 어노테이션 추가해야 작동함
* PrePersist, PostPersist
* PreUpdate, PostUpdate
*
* */
@MappedSuperclass
@Getter
public class JpaBaseEntity
{
	@Column(updatable = false)
	private LocalDateTime createdDate;

	private LocalDateTime updatedDate;

	/**
	 * JPA가 이벤트 :
	 */
	@PrePersist
	public void prePersist()
	{
		LocalDateTime now = LocalDateTime.now();
		createdDate = now;
		updatedDate = now;
	}

	/**
	 * JPA 이벤트
	 */
	@PreUpdate
	public void preuUpdate()
	{
		updatedDate = LocalDateTime.now();
	}
}
