package com.sejong.project.pm.exercise.dto;

import java.time.LocalDateTime;

public class ExerciseResponse {
    public record searchResultDto(
        String exerciseName,
        int exerciseCaloriesHour
    ){}

    public record doingExerciseDto(
            String exerciseName,
            int exerciseCalories,
            LocalDateTime exerciseDate
    ){}
}
