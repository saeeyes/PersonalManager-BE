package com.sejong.project.pm.food.dto;

import com.sejong.project.pm.food.MemberFood;
import jakarta.validation.constraints.NotEmpty;

public class FoodRequest {
    public record searchFoodDto(
            @NotEmpty String word
    ){}

    public record AddFoodDTO(
            @NotEmpty String foodName,
            @NotEmpty int foodCalories,
            @NotEmpty String manufacturingCompany,
            @NotEmpty double protein,
            @NotEmpty double carbohydrate,
            @NotEmpty double fat,
             double dietaryFiber,
             double sodium,
             double sugar
    ){}

    public record AddEatingFood(
       MemberFood.FoodTime foodTime,
       @NotEmpty String foodname
    ){}

    public record FoodIdDto(
       @NotEmpty Long id
    ){}

}
