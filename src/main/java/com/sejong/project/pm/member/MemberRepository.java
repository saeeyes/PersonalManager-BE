package com.sejong.project.pm.member;

import com.sejong.project.pm.exercise.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(long id);
}
