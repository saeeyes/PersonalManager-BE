package com.sejong.project.pm.exercise.dto;

public class ExerciseRequest {
    public record saveExerciseDto(
            String exerciseName,
            int exerciseCaloriesHour
    ){}

    public record searchExerciseDto(
            String exerciseName
    ){}
}
