package com.inho.datajpa;

import com.inho.datajpa.dto.*;
import com.inho.datajpa.entity.Member;
import com.inho.datajpa.entity.QMember;
import com.inho.datajpa.entity.Team;
import com.inho.datajpa.repository.MemberJpaRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static com.inho.datajpa.entity.QMember.member;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

@SpringBootTest
@Transactional
@Rollback(false)
public class QueryDslBasicTest {
	@PersistenceContext
	private EntityManager em;
	@Autowired
	private MemberJpaRepository memberJpaRepository;


	@BeforeEach
	@Commit
	public void beforeEach()
	{
		Team teamA = new Team("teamA");
		Team teamB = new Team("teamB");

		em.persist(teamA);
		em.persist(teamB);

		Team team = null;
		for (int i=1; i < 11; i++){
			team =  ( i % 2 == 0 ) ? teamA : teamB;
			em.persist(new Member("member" + i, i * 10, teamA));
		}
		em.persist(new Member(null, 100, teamA));
	}

	@Test
	public void startJPQL()
	{
		Member findMember = em.createQuery("select m from Member m where m.username = :username", Member.class)
				.setParameter("username", "member1")
				.getSingleResult();

		assertThat(findMember.getUsername()).isEqualTo("member1");

	}

	@Test
	public void startQueryDsl()
	{
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);
		QMember m = new QMember("m");

		Member findMember = queryFactory
				.select(m)
				.from(m)
				.where(m.username.eq("member1"))
				.fetchOne();

		assertThat(findMember.getUsername()).isEqualTo("member1");
	}

	@Test
	public void startQueryDsl1()
	{
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);

		Member findMember = queryFactory
				.select(member)
				.from(member)
				.where(member.username.eq("member1"))
				.fetchOne();

		assertThat(findMember.getUsername()).isEqualTo("member1");
	}


	@Test
	public void search()
	{
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);

		Member findMember = queryFactory
				.selectFrom(member)
				.where(
						member.username.eq("member1")
						.and( member.age.eq(10) )
				).fetchOne();

		assertThat(findMember.getUsername()).isEqualTo("member1");
		/*
			 1) username.eq("member1")       :   usesrname  = 'member1'
			 2) username.ne("member1")       :   usesrname != 'member1'
			 3) username.eq("member1").not() :   usesrname != 'member1'

			 4) username.isNotNull()         : username is not null
			 5) age.in(10,20)                : age in ( 10, 20 )
			 6) age.notIn(10,20)             : age not in ( 10, 20)
			 7) age.between(10,30)           : between 10 and 30

			 8) age.goe(30)                  : age >= 30
			 9) age.gt(30)                   : age > 30
			10) age.loe(30)                  : age <= 30
			11) age.lt(30)                   : age < 30

			12) username.like("member%")     : username like 'member%'
			13) username.contain("member")   : username like '%member%'
			14) username.startsWith("member"): username like 'member%'

		* */
	}


	@Test
	public void searchAndParam() {
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);

		// .and() 대신 콤마(,) 로 대체가능
		Member findMember = queryFactory
				.selectFrom(member)
				.where(
						member.username.eq("member1"),
						member.age.eq(10)
				).fetchOne();
	}

	@Test
	public void searchResult() {
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);

		System.out.println("------------- fetch() ----------------");
		List<Member> fetch = queryFactory
				.selectFrom(member)
				.fetch();

		System.out.println("------------- fetchOne() ----------------");
		Member fetchOne = queryFactory
				.selectFrom(member)
				.where(member.username.like("%member"))
				.limit(1)
				.fetchOne();

		System.out.println("------------- fetchFirst() ----------------");
		Member fetchFirst = queryFactory
				.selectFrom(member)
				.fetchFirst();

		System.out.println("------------- fetchResults() ----------------");
		QueryResults<Member> fetchResults = queryFactory
				.selectFrom(member)
				.fetchResults();

		List<Member> contents = fetchResults.getResults();
		long total = fetchResults.getTotal();
		long limit = fetchResults.getLimit();
		long offset = fetchResults.getOffset();
		System.out.println("contents = " + contents);
		System.out.println("total = " + total);
		System.out.println("limit = " + limit);
		System.out.println("offset = " + offset);

		System.out.println("------------- fetchCount() ----------------");
		long fetchCount = queryFactory
				.selectFrom(member)
				.fetchCount();
		
		/*
			결과 조회
			1) fetch()          : 리스트 조회, 데이터 없으면 빈 리스트 반환
			2) fetchOne()       : 단 건 조회, 데이터 없으면 null, 둘 이상이면 NonUniqueResultException 발생
			3) fetchFirst()     : limit(1).fetchOne()
			4) fetchResults()   : 페이징 정보 포함, total count 쿼리 추가 실행
			5) fetchCount()     : count 쿼리로 변경해서 count 수 조회
		* */
	}


	@Test
	public void sort() {
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);

		System.out.println("------------- sort() ----------------");
		List<Member> fetch = queryFactory
				.selectFrom(member)
				.where( member.age.eq(100) )
				.orderBy( member.age.desc(), member.username.asc().nullsLast() )
				.fetch();

		System.out.println("fetch = " + fetch);
	}


	@Test
	public void paging() {
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);

		System.out.println("------------- sort() ----------------");
		List<Member> fetch = queryFactory
				.selectFrom(member)
				.where( member.age.eq(100) )
				.orderBy( member.age.desc(), member.username.asc().nullsLast() )
				.fetch();

		System.out.println("fetch = " + fetch);
	}

	@Test
	public void findDtoBySetter() {
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);

		System.out.println("------------- projection by setter() ----------------");
		List<MemberDto> fetch = queryFactory
				.select(Projections.bean(MemberDto.class,
						member.username))
				.from(member)
				.fetch();

		System.out.println("fetch = " + fetch);
	}

	@Test
	public void findDtoByFields() {
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);

		System.out.println("------------- projection by field() ----------------");
		List<MemberDto> fetch = queryFactory
				.select(Projections.fields(MemberDto.class,
						member.username))
				.from(member)
				.fetch();

		System.out.println("fetch = " + fetch);
	}

	@Test
	public void findDtoByConstructor() {
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);

		System.out.println("------------- projection by constructor() ----------------");
		List<MemberDto> fetch = queryFactory
				.select(Projections.constructor(MemberDto.class,
						member.username))
				.from(member)
				.fetch();

		System.out.println("fetch = " + fetch);
	}


	@Test
	public void findDtoByEtc() {
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);

		System.out.println("------------- projection by constructor() ----------------");
		List<UserDto> fetch = queryFactory
				.select(Projections.fields(UserDto.class,
						member.username.as("name"),
						member.age))
				.from(member)
				.fetch();

		System.out.println("fetch = " + fetch);
	}


	@Test
	public void findDtoBy() {
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);
		QMember sm = new QMember("sm");

		System.out.println("------------- projection by @QueryProject alias() ----------------");
		List<UserDto> fetch = queryFactory
				.select(Projections.fields(UserDto.class,
						member.username.as("name"),
						ExpressionUtils.as( JPAExpressions
								.select(sm.age.max())
								.from(sm), "age" )
				))
				.from(member)
				.fetch();

		System.out.println("fetch = " + fetch);
	}

	@Test
	public void findDtoByQueryProejct() {
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);
		QMember sm = new QMember("sm");

		//
		System.out.println("------------- findDtoByQueryProejct ----------------");
		List<UserDto> fetch = queryFactory
				.select(new QUserDto(member.username, member.age))
				.from(member)
				.fetch();
		System.out.println("fetch = " + fetch);
	}


	/**
	    동적쿼리 해결방법
	    1) BooleanBuilder
	    2) Where 다중 파라메타 사용
	 * */
	@Test
	public void dynamicQuery_BooleanBuilder() {
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);
		QMember sm = new QMember("sm");
		
		String usernameParam = "member1";
		Integer ageParam = 1;

		// BooleanBuilder 사용한 동적쿼리
		List<Member> result1 = searchMember1(usernameParam, ageParam);
		
		// Where 다중 파라메타를 사용한 동적쿼리
		List<Member> result2 = searchMember2(usernameParam, ageParam);

	}

	private List<Member> searchMember1(String usernameParam, Integer ageParam) {
		BooleanBuilder builder = new BooleanBuilder();
		if ( usernameParam != null ){
			builder.and(member.username.eq(usernameParam));
		}
		if ( ageParam != null ){
			builder.and(member.age.eq(ageParam));
		}

		JPAQueryFactory queryFactory = new JPAQueryFactory(em);
		return queryFactory
				.selectFrom(member)
				.where( builder )
				.fetch();
	}

	private List<Member> searchMember2(String usernameParam, Integer ageParam) {

		// usernameEq, ageEq 반환값이 null 이면 무시되면서 동적쿼리를 생성한다.
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);
		return queryFactory
				.selectFrom(member)
				.where( usernameEq(usernameParam).
						or(ageEq(ageParam)) )
				.fetch();
	}


	private BooleanExpression usernameEq(String usernameParam) {
		if ( usernameParam == null )
			return null;
		return member.username.eq(usernameParam);
	}

	private BooleanExpression ageEq(Integer ageParam) {
		if ( ageParam == null )
			return null;
		return member.age.eq(ageParam);
	}



	@Test
	@Commit
	public void update_bulk() {
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);

		long affected = queryFactory
				.update(member)
					.set(member.username, "전체통일")
					.set(member.age, member.age.add(10))
				.where(member.age.lt(28))
				.execute();

		System.out.println("affected = " + affected);
	}



	@Test
	@Commit
	public void delete_bulk() {
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);

		long affected = queryFactory
				.delete(member)
				.where(member.age.lt(28))
				.execute();

		System.out.println("affected = " + affected);
	}


	@Test
	@Commit
	public void testR() {
		MemberSearchCondition cond = new MemberSearchCondition();
		cond.setAgeGoe(50);
		cond.setTeamName("teamA");
		List<MemberTeamDto> memberTeamDtos = memberJpaRepository.searchByBuilder(cond);
		for (MemberTeamDto memberTeamDto : memberTeamDtos) {
			System.out.println("memberTeamDto = " + memberTeamDto);
		}

		List<MemberTeamDto> search = memberJpaRepository.search(cond);
		for (MemberTeamDto memberTeamDto : search) {
			System.out.println("memberTeamDto = " + memberTeamDto);
		}
	}
}
