package com.sejong.project.pm.food.service;

import com.sejong.project.pm.food.MemberFood;
import com.sejong.project.pm.food.dto.FoodRequest.searchFoodDto;
import com.sejong.project.pm.food.dto.FoodRequest.AddFoodDTO;
import com.sejong.project.pm.food.dto.FoodResponse.SearchFood;
import com.sejong.project.pm.food.dto.FoodResponse.FoodDTO;
import com.sejong.project.pm.member.dto.MemberDetails;

import java.time.LocalDateTime;
import java.util.List;

public interface FoodService {
    List<SearchFood> searchFood(MemberDetails member,searchFoodDto searchFoodDto);
    List<SearchFood> searchAllFood(MemberDetails member);

    List<SearchFood> getEatingFood(MemberDetails member);
    List<SearchFood> getEatingFoodToday(MemberDetails member);

    boolean checkDate(LocalDateTime created);
    List<MemberFood> getMemberFood(MemberDetails member);

    List<FoodDTO> addFood(MemberDetails member, AddFoodDTO request);
    List<FoodDTO> addEatingFood(MemberDetails member, searchFoodDto request);

}
