package com.inho.datajpa.dto;

import lombok.Data;

@Data
public class MemberDto {
	private String userName;
	private int age;
	private String teamName;

	public MemberDto() {
	}
	public MemberDto(String userName) {
		this.userName = userName;
	}
	public MemberDto(String userName, int age, String teamName) {
		this.userName = userName;
		this.age = age;
		this.teamName = teamName;
	}
}
