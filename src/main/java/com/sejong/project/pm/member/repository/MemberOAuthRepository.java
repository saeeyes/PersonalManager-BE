package com.sejong.project.pm.member.repository;

import com.sejong.project.pm.member.model.MemberOAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberOAuthRepository extends JpaRepository<MemberOAuth, Long> {
}
