package com.inho.datajpa.repository;

import com.inho.datajpa.entity.Member;
import com.inho.datajpa.entity.Team;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TeamJpaRepository
{
	@PersistenceContext
	private EntityManager em;

	public Team save(Team team)
	{
		em.persist(team);
		return team;
	}

	public Team find(Long id){
		return em.find(Team.class, id);
	}


}
