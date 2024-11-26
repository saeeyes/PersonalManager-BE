package com.sejong.project.pm.food.controller;

import com.sejong.project.pm.food.dto.FoodRequest;
import com.sejong.project.pm.food.dto.FoodRequest.AddFoodDTO;
import com.sejong.project.pm.food.dto.FoodRequest.searchFoodDto;
import com.sejong.project.pm.food.service.FoodService;
import com.sejong.project.pm.global.exception.BaseResponse;
import com.sejong.project.pm.member.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class FoodController {

    @Autowired
    private FoodService foodService;

    @GetMapping("/food/search")
    private BaseResponse<?> searchFood(@AuthenticationPrincipal MemberDetails member, @RequestParam(value = "foodId",required = true) Long searchFoodDto){
        return BaseResponse.onSuccess(foodService.searchFood(searchFoodDto));
    }

    @GetMapping("/food/searchList")
    private BaseResponse<?> searchList(@AuthenticationPrincipal MemberDetails member, @RequestParam("word") searchFoodDto request){
        return BaseResponse.onSuccess(foodService.searchFoodList(request));
    }

    @GetMapping("/food/searchAll")
    private BaseResponse<?> searchAllFood(@AuthenticationPrincipal MemberDetails member){
        return BaseResponse.onSuccess(foodService.searchAllFood());
    }

    @GetMapping("/food/getEatingFoodToday")
    private BaseResponse<?> getEatingFoodToday(@AuthenticationPrincipal MemberDetails member){
        return BaseResponse.onSuccess(foodService.getEatingFoodToday(member));
    }

    @GetMapping("/food/getEatingFood")
    private BaseResponse<?> getEatingFood(@AuthenticationPrincipal MemberDetails member){
        return BaseResponse.onSuccess(foodService.getAllEatingFood(member));
    }

    @PostMapping("/food/addFood")
    private BaseResponse<?> addFood(@AuthenticationPrincipal MemberDetails member, @RequestBody AddFoodDTO request){
        return BaseResponse.onSuccess(foodService.addFood(member,request));
    }

    @PostMapping("/food/addEatingFood")
    private BaseResponse<?> addEatingFood(@AuthenticationPrincipal MemberDetails member, @RequestBody FoodRequest.AddEatingFood request){
        return BaseResponse.onSuccess(foodService.addEatingFood(member,request));
    }

    @DeleteMapping("/food/deleteEatingFood")
    private BaseResponse<?> deleteEatingFood(@AuthenticationPrincipal MemberDetails member, @RequestBody FoodRequest.FoodIdDto request){
        return BaseResponse.onSuccess(foodService.deleteEatingFood(member,request));
    }

    @GetMapping("/food/eatingByDate")
    private BaseResponse<?> eatingByDate(@AuthenticationPrincipal MemberDetails member, @RequestParam("date") LocalDate request){
        System.out.println(request);
        return BaseResponse.onSuccess(foodService.eatingByDate(member,request));
    }

    @GetMapping("/food/targetCalories")
    private BaseResponse<?> targetCalories(@AuthenticationPrincipal MemberDetails member){
        return BaseResponse.onSuccess(foodService.targetCalories(member));
    }


    //=============================================수정 필요


}
