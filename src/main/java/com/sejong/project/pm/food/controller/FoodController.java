package com.sejong.project.pm.food.controller;

import com.sejong.project.pm.food.dto.FoodRequest.AddFoodDTO;
import com.sejong.project.pm.food.dto.FoodRequest.searchFoodDto;
import com.sejong.project.pm.food.service.FoodService;
import com.sejong.project.pm.global.exception.BaseResponse;
import com.sejong.project.pm.member.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FoodController {

    @Autowired
    private FoodService foodService;

    @GetMapping("/food/search")
    private BaseResponse<?> searchFood(@AuthenticationPrincipal MemberDetails member, @RequestBody searchFoodDto searchFoodDto){
        return BaseResponse.onSuccess(foodService.searchFood(member, searchFoodDto));
    }

    @GetMapping("/food/searchAll")
    private BaseResponse<?> searchAllFood(@AuthenticationPrincipal MemberDetails member){
        return BaseResponse.onSuccess(foodService.searchAllFood(member));
    }

    @GetMapping("/food/getEatingFoodToday")
    private BaseResponse<?> getEatingFoodToday(@AuthenticationPrincipal MemberDetails member){
        return BaseResponse.onSuccess(foodService.getEatingFoodToday(member));
    }

    @GetMapping("/food/getEatingFood")
    private BaseResponse<?> getEatingFood(@AuthenticationPrincipal MemberDetails member){
        return BaseResponse.onSuccess(foodService.getEatingFood(member));
    }

    @PostMapping("/food/addFood")
    private BaseResponse<?> addFood(@AuthenticationPrincipal MemberDetails member, @RequestBody AddFoodDTO request){
        return BaseResponse.onSuccess(foodService.addFood(member,request));
    }

    @PostMapping("/food/addEatingFood")
    private BaseResponse<?> addEatingFood(@AuthenticationPrincipal MemberDetails member, @RequestBody searchFoodDto request){
        return BaseResponse.onSuccess(foodService.addEatingFood(member,request));
    }

    //=============================================수정 필요


}
