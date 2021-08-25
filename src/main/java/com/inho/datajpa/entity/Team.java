package com.inho.datajpa.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString(exclude = {"members"})
public class Team
{
	@Id
	@GeneratedValue
	@Column(name="team_id")
	private Long id;

	private String name;

	@OneToMany(mappedBy = "team")
	private List<Member> members = new ArrayList<>();

	protected Team() {}

	public Team(String name) {
		this.name = name;
	}
}
