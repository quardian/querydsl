package com.inho.datajpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.Assert;

import javax.persistence.*;

@Entity
@Getter
@ToString(exclude = {"team"})
public class Member
{
	@Id
	@GeneratedValue
	@Column(name="member_id")
	private Long id;

	private String username;

	private int age;

	@JoinColumn(name = "team_id")
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Team team;

	public void chagneTeam(Team team){
		Assert.notNull(team);
		this.team = team;
		team.getMembers().add(this);
	}

	protected Member() { }

	public Member(String username) {
		this.username = username;
	}

	public Member(String username, int age) {
		this.username = username;
		this.age = age;
	}

	public Member(String username, int age, Team team) {
		this.username = username;
		this.age = age;
		if ( team != null ){
			chagneTeam(team);
		}
	}
}
