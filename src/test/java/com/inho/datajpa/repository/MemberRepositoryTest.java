package com.inho.datajpa.repository;

import com.inho.datajpa.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {
	@Autowired
	MemberRepository memberRepository;

	@Test
	void save() {
		Member member = new Member("이인호");
		Member saveMember = memberRepository.save(member);

		Member findMember = memberRepository.findById(saveMember.getId()).get();
		Assertions.assertThat(saveMember.getId()).isEqualTo(findMember.getId());
	}


}