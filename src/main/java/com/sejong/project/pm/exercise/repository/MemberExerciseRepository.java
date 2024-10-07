package com.sejong.project.pm.exercise.repository;

import com.sejong.project.pm.exercise.MemberExercise;
import com.sejong.project.pm.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberExerciseRepository extends JpaRepository<MemberExercise,Long> {
    List<MemberExercise> findByMember(Member member);
}
