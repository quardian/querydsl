package com.inho.datajpa.repository;

import com.inho.datajpa.dto.MemberDto;
import com.inho.datajpa.dto.MemberSearchCondition;
import com.inho.datajpa.dto.MemberTeamDto;
import com.inho.datajpa.dto.QMemberTeamDto;
import com.inho.datajpa.entity.Member;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.thymeleaf.util.StringUtils;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static com.inho.datajpa.entity.QMember.member;
import static com.inho.datajpa.entity.QTeam.team;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
	List<Member> findByUsername(String username);
}
