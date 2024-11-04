package com.sejong.project.pm.food.repository;

import com.sejong.project.pm.food.Food;
import com.sejong.project.pm.food.dto.FoodResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food,Long> {
    List<Food> findByFoodName(String foodname);
}
