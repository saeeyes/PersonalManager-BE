package com.sejong.project.pm.food.service;

import com.sejong.project.pm.food.Food;
import com.sejong.project.pm.food.MemberFood;
import com.sejong.project.pm.food.dto.FoodRequest;
import com.sejong.project.pm.food.dto.FoodRequest.searchFoodDto;
import com.sejong.project.pm.food.dto.FoodRequest.AddFoodDTO;
import com.sejong.project.pm.food.dto.FoodResponse;
import com.sejong.project.pm.food.dto.FoodResponse.SearchFood;
import com.sejong.project.pm.food.dto.FoodResponse.FoodDTO;
import com.sejong.project.pm.member.dto.MemberDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface FoodService {
    FoodDTO searchFood(Long searchFoodDto);
    List<SearchFood> searchAllFood();
    List<SearchFood> searchFoodList(searchFoodDto request);
//    FoodResponse.foodByDateDto eatingByDate(MemberDetails memberDetails, FoodRequest.DateDto request);
FoodResponse.foodByDateDto eatingByDate(MemberDetails memberDetails, LocalDate request);

    List<FoodDTO> getAllEatingFood(MemberDetails member);
    List<FoodDTO> getEatingFoodToday(MemberDetails member);

    boolean checkDate(LocalDateTime created);
    List<MemberFood> getMemberFood(MemberDetails member);

    FoodDTO addFood(MemberDetails member, AddFoodDTO request);
    List<FoodResponse.eatingFoodDTO> addEatingFood(MemberDetails member, FoodRequest.AddEatingFood request);

    List<FoodDTO> deleteEatingFood(MemberDetails member, FoodRequest.FoodIdDto request);
    List<Integer> targetCalories(MemberDetails memberDetails);
}
