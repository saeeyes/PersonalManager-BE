package com.sejong.project.pm.exercise.dto;

public class ExerciseResponse {
    public record searchResultDto(
        String exerciseName,
        int exerciseCaloriesHour
    ){}
}
