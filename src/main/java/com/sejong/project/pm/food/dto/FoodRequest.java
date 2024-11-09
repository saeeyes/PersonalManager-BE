package com.sejong.project.pm.food.dto;

import jakarta.validation.constraints.NotEmpty;

public class FoodRequest {
    public record searchFoodDto(
            @NotEmpty String foodname
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

}
