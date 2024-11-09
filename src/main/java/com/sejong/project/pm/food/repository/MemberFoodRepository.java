package com.sejong.project.pm.food.repository;

import com.sejong.project.pm.food.MemberFood;
import com.sejong.project.pm.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberFoodRepository extends JpaRepository<MemberFood, Long> {
    List<MemberFood> findByMember(String username);
}
