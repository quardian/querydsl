package com.inho.datajpa.repository;

import com.inho.datajpa.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberJpaRepositoryTest {
	@Autowired
	MemberJpaRepository memberJpaRepository;

	@Test
	void save() {
		Member member = new Member("이인호");
		Member saveMember = memberJpaRepository.save(member);

		Member findMember = memberJpaRepository.find(saveMember.getId());
		Assertions.assertThat(saveMember.getId()).isEqualTo(findMember.getId());
	}


}