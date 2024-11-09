package com.sejong.project.pm.food.dto;

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
}
