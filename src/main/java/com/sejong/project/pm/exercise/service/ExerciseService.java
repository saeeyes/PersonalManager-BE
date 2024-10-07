package com.sejong.project.pm.exercise.service;

import com.sejong.project.pm.exercise.dto.ExerciseRequest;
import com.sejong.project.pm.exercise.dto.ExerciseResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExerciseService {
    void saveExercise(ExerciseRequest.saveExerciseDto saveExercise);
    List<ExerciseResponse.searchResultDto> searchExercise(ExerciseRequest.searchExerciseDto searchexercise);

}
