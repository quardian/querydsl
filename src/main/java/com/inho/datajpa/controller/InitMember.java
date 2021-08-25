package com.inho.datajpa.controller;

import com.inho.datajpa.entity.Member;
import com.inho.datajpa.entity.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitMember {

	private final InitMemberService initMemberService;

	@PostConstruct
	public void init()
	{
		initMemberService.init();
	}


	@Component
	@RequiredArgsConstructor
	static class InitMemberService{
		@PersistenceContext
		private final EntityManager em;

		@Transactional
		public void init(){
			Team teamA = new Team("teamA");
			Team teamB = new Team("teamB");

			em.persist(teamA);
			em.persist(teamB);

			Team team = null;
			for (int i=1; i < 100; i++){
				team =  ( (i % 2) == 0 ) ? teamA : teamB;
				em.persist(new Member("member" + i, i , team));
			}

			em.persist(new Member(null, 100, teamA));

			em.flush();
			em.clear();

			Long insertCount = em.createQuery("select count(m) from Member m", Long.class).getSingleResult();
			System.out.println("insertCount = " + insertCount);
		}
	}
}
