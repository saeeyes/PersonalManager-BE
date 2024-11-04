package com.sejong.project.pm.food.dto;

public class FoodResponse{
    public record searchFood(
            String foodname,
            int foodCalories,
            String manufacturingCompany
    ){}
}
