package com.member.members.dao;

import com.member.members.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberDAO extends JpaRepository<Member, Integer> {
}
