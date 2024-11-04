package com.sejong.project.pm.food.service;

import com.sejong.project.pm.food.Food;
import com.sejong.project.pm.food.dto.FoodRequest;
import com.sejong.project.pm.food.dto.FoodResponse;
import com.sejong.project.pm.food.repository.FoodRepository;
import com.sejong.project.pm.global.handler.MyExceptionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.sejong.project.pm.global.exception.codes.ErrorCode.BAD_REQUEST_ERROR;

@Service
@Slf4j
@RequiredArgsConstructor
public class FoodServicelmpl implements FoodService{

    private FoodRepository foodRepository;

    public List<FoodResponse.searchFood> searchFood(FoodRequest.searchFoodDto searchFoodDto){
        List<Food> foodList = foodRepository.findByFoodName(searchFoodDto.foodname());
        if(foodList.isEmpty() || foodList==null) throw new MyExceptionHandler(BAD_REQUEST_ERROR);

        List<FoodResponse.searchFood> searchFoods = new ArrayList<>();

        for(Food fd : foodList){
            //탄단지 추가는 조금 나중에 해야할듯

            searchFoods.add(new FoodResponse.searchFood(
                    fd.getFoodName(),
                    fd.getFoodCalories(),
                    fd.getManufacturingCompany()
            ));
        }
        return searchFoods;
    }
    public List<FoodResponse.searchFood> searchAllFood(){
        List<Food> foodList = foodRepository.findAll();
        if(foodList.isEmpty() || foodList==null) throw new MyExceptionHandler(BAD_REQUEST_ERROR);

        List<FoodResponse.searchFood> searchFoods = new ArrayList<>();

        for(Food fd : foodList){
            //탄단지 추가는 조금 나중에 해야할듯

            searchFoods.add(new FoodResponse.searchFood(
                    fd.getFoodName(),
                    fd.getFoodCalories(),
                    fd.getManufacturingCompany()
            ));
        }
        return searchFoods;
    }
}
