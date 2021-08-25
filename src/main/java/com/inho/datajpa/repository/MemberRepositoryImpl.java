package com.inho.datajpa.repository;

import com.inho.datajpa.dto.MemberSearchCondition;
import com.inho.datajpa.dto.MemberTeamDto;
import com.inho.datajpa.dto.QMemberTeamDto;
import com.inho.datajpa.entity.Member;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

import static com.inho.datajpa.entity.QMember.member;
import static com.inho.datajpa.entity.QTeam.team;

public class MemberRepositoryImpl implements MemberRepositoryCustom{
	@PersistenceContext
	private EntityManager em;
	private final JPAQueryFactory queryFactory;

	public MemberRepositoryImpl(EntityManager em) {
		this.em = em;
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public List<MemberTeamDto> searchByBuilder(MemberSearchCondition condition) {

		BooleanBuilder builder = new BooleanBuilder();

		if (!StringUtils.isEmpty(condition.getUserName())) {
			builder.and( member.username.eq(condition.getUserName()));
		}
		if (!StringUtils.isEmpty(condition.getTeamName())) {
			builder.and( team.name.eq(condition.getTeamName()));
		}
		if ( condition.getAgeGoe() != null ){
			builder.and( member.age.goe( condition.getAgeGoe()) );
		}
		if ( condition.getAgeLoe() != null ){
			builder.and( member.age.loe( condition.getAgeLoe()) );
		}

		return queryFactory
				.select( new QMemberTeamDto(
						member.id.as("memberId"),
						member.username,
						member.age,
						team.id.as("teamId"),
						team.name.as("teamName")) )
				.from(member)
				.join(member.team, team)
				.where(builder)
				.fetch();

	}

	@Override

	public List<MemberTeamDto> search(MemberSearchCondition condition)
	{
		return queryFactory
				.select( new QMemberTeamDto(
						member.id.as("memberId"),
						member.username,
						member.age,
						team.id.as("teamId"),
						team.name.as("teamName")) )
				.from(member)
				.join(member.team, team)
				.where(     usernameEq( condition.getUserName() ),
						( teamNameEq(condition.getTeamName()) ),
						( ageGoe(condition.getAgeGoe()) ),
						( ageLoe(condition.getAgeLoe()) )
				)
				.fetch();
	}

	private BooleanExpression ageLoe(Integer ageLoe) {
		return (ageLoe != null) ? member.age.loe(ageLoe) : null;
	}

	private BooleanExpression ageGoe(Integer ageGoe) {
		return (ageGoe != null) ? member.age.goe(ageGoe) : null;
	}

	private BooleanExpression teamNameEq(String teamName) {
		return (!StringUtils.isEmpty(teamName)) ? team.name.eq(teamName) : null;
	}

	private BooleanExpression usernameEq(String userName) {
		return (!StringUtils.isEmpty(userName)) ? member.username.eq(userName) : null;
	}
}
