package com.inho.datajpa.repository;

import com.inho.datajpa.dto.MemberDto;
import com.inho.datajpa.entity.Member;
import com.inho.datajpa.entity.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {
	@PersistenceContext
	EntityManager em;

	@Test
	void save() {

	}

}