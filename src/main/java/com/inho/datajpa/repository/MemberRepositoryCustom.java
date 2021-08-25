package com.inho.datajpa.repository;


import com.inho.datajpa.dto.MemberSearchCondition;
import com.inho.datajpa.dto.MemberTeamDto;
import com.inho.datajpa.entity.Member;

import java.util.List;

public interface MemberRepositoryCustom {
	List<MemberTeamDto> searchByBuilder(MemberSearchCondition condition);
	List<MemberTeamDto> search(MemberSearchCondition condition);
}
