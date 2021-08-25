package com.inho.datajpa.repository;

import com.inho.datajpa.dto.MemberDto;
import com.inho.datajpa.entity.Member;
import com.inho.datajpa.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {


}
