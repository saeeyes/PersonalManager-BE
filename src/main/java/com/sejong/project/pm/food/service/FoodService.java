package com.sejong.project.pm.food.service;

import com.sejong.project.pm.food.dto.FoodRequest;
import com.sejong.project.pm.food.dto.FoodResponse;

import java.util.List;

public interface FoodService {
    List<FoodResponse.searchFood> searchFood(FoodRequest.searchFoodDto searchFoodDto);
    List<FoodResponse.searchFood> searchAllFood();
}
