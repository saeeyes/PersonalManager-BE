package com.sejong.project.pm.exercise.service;

import com.sejong.project.pm.exercise.dto.ExerciseRequest;
import com.sejong.project.pm.exercise.dto.ExerciseResponse;

import java.util.List;

public interface ExerciseService {

    void addDoingExercise(ExerciseRequest.doingExerciseDto doingExerciseDto);
    List<ExerciseResponse.doingExerciseDto> showTodayExerciseList();

    void saveExercise(ExerciseRequest.saveExerciseDto saveExercise);
    List<ExerciseResponse.searchResultDto> searchExercise(ExerciseRequest.searchExerciseDto searchexercise);
    List<ExerciseResponse.searchResultDto> searchAllExercise();
}
