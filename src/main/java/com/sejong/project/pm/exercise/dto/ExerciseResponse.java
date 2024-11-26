package com.sejong.project.pm.exercise.dto;

import java.time.LocalDateTime;

public class ExerciseResponse {
    public record searchResultDto(
        String exerciseName,
        int exerciseCaloriesHour,
        Long exerciseId
    ){}

    public record doingExerciseDto(
            String exerciseName,
            int exerciseCalories,
            double exerciseTime,
            Long exerciseId
    ){}
}
