package com.inho.datajpa.repository;

import com.inho.datajpa.entity.Member;
import com.inho.datajpa.entity.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberJpaRepositoryTest
{
	@PersistenceContext
	EntityManager em;

	@Autowired
	MemberJpaRepository memberJpaRepository;

	@Autowired
	TeamJpaRepository teamJpaRepository;

	@Test
	void save() {

	}

}