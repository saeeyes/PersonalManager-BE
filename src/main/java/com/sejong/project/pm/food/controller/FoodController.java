package com.sejong.project.pm.food.controller;

import com.sejong.project.pm.food.dto.FoodRequest;
import com.sejong.project.pm.food.service.FoodService;
import com.sejong.project.pm.global.exception.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FoodController {

    @Autowired
    private FoodService foodService;

    @GetMapping("/food/search")
    private BaseResponse<?> searchFood(@RequestBody FoodRequest.searchFoodDto searchFoodDto){
        return BaseResponse.onSuccess(foodService.searchFood(searchFoodDto));
    }

    @GetMapping("/food/searchAll")
    private BaseResponse<?> searchAllFood(){
        return BaseResponse.onSuccess(foodService.searchAllFood());
    }
}
