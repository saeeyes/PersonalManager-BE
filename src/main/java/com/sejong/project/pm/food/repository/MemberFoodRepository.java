package com.sejong.project.pm.food.repository;

import com.sejong.project.pm.food.MemberFood;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberFoodRepository extends JpaRepository<MemberFood, Long> {
}
