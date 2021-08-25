package com.inho.datajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
//@EnableJpaAuditing
public class DataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataJpaApplication.class, args);
	}


	/**
	 * @CreatedBy, @LastModifiedBy 호출시 마다시 호출됨
	 * @return
	 */
	/*@Bean
	public AuditorAware<String> auditorProvider(){
		*//* 실제 구현 시에는 SESSION 정보 반환 *//*
		return () -> Optional.of(UUID.randomUUID().toString());
	}*/
}
