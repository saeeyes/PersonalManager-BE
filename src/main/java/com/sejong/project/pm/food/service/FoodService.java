package com.sejong.project.pm.food.service;

import com.sejong.project.pm.food.Food;
import com.sejong.project.pm.food.MemberFood;
import com.sejong.project.pm.food.dto.FoodRequest;
import com.sejong.project.pm.food.dto.FoodRequest.searchFoodDto;
import com.sejong.project.pm.food.dto.FoodRequest.AddFoodDTO;
import com.sejong.project.pm.food.dto.FoodResponse.SearchFood;
import com.sejong.project.pm.food.dto.FoodResponse.FoodDTO;
import com.sejong.project.pm.member.dto.MemberDetails;

import java.time.LocalDateTime;
import java.util.List;

public interface FoodService {
    List<SearchFood> searchFood(searchFoodDto searchFoodDto);
    List<SearchFood> searchAllFood(MemberDetails member);

    List<FoodDTO> getEatingFood(MemberDetails member);
    List<FoodDTO> getEatingFoodToday(MemberDetails member);

    boolean checkDate(LocalDateTime created);
    List<MemberFood> getMemberFood(MemberDetails member);

    FoodDTO addFood(MemberDetails member, AddFoodDTO request);
    FoodDTO addEatingFood(MemberDetails member, FoodRequest.AddEatingFood request);

    List<FoodDTO> deleteEatingFood(MemberDetails member, FoodRequest.DeleteEatingFood request);

    Food getFood(String foodName);
}
