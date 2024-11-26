package com.sejong.project.pm.food.dto;

import com.sejong.project.pm.food.Food;
import com.sejong.project.pm.food.MemberFood;

import java.util.List;

public class FoodResponse{
    public record SearchFood(
            String foodname,
            int foodCalories,
            String manufacturingCompany
    ){}

    public record FoodDTO(
            String foodName,
            int foodCalories,
            String manufacturingCompany,
            double protein,
            double carbohydrate,
            double fat
    ){}

    public record eatingFoodDTO(
            String foodName,
            int foodCalories,
            String manufacturingCompany,
            double protein,
            double carbohydrate,
            double fat,
            MemberFood.FoodTime foodTime,
            double eatingAmoung
    ){}

    public record foodByDateDto(
            List<Integer> targetCalories,
            List<Integer> nowCalories,
            List<SearchFood> morning,
            List<SearchFood> lunch,
            List<SearchFood> dinner
    ){}
}
