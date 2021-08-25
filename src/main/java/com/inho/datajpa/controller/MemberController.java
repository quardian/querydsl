package com.inho.datajpa.controller;

import com.inho.datajpa.dto.MemberSearchCondition;
import com.inho.datajpa.dto.MemberTeamDto;
import com.inho.datajpa.entity.Member;
import com.inho.datajpa.repository.MemberJpaRepository;
import com.inho.datajpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MemberController
{
	private final MemberRepository memberRepository;
	private final MemberJpaRepository memberJpaRepository;

	@GetMapping(value="/members")
	public ResponseEntity findMembers(MemberSearchCondition condition)
	{
		List<MemberTeamDto> search = memberRepository.search(condition);
		return new ResponseEntity(search, HttpStatus.OK);
	}
}
