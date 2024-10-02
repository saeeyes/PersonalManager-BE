package com.sejong.project.pm.exercise.service;

import com.sejong.project.pm.exercise.Exercise;
import com.sejong.project.pm.exercise.dto.ExerciseRequest;
import com.sejong.project.pm.exercise.dto.ExerciseResponse;
import com.sejong.project.pm.exercise.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExerciseSergicelmpl implements ExerciseService{

    @Autowired
    public final ExerciseRepository exerciseRepository;

    public void saveExercise(ExerciseRequest.saveExerciseDto saveExercise){
        Exercise exercise = Exercise.createExercise(saveExercise);
        exerciseRepository.save(exercise);
    }

    public List<ExerciseResponse.searchResultDto> searchExercise(ExerciseRequest.searchExerciseDto searchexercise){
        List<ExerciseResponse.searchResultDto> searchResultLlist = new ArrayList<>();

        List<Exercise> searchFromDb = exerciseRepository.findByExercisieNameStartingWith(searchexercise.exerciseName());
        for(Exercise e : searchFromDb){
            searchResultLlist.add(
                    new ExerciseResponse.searchResultDto(e.getExerciseName(),e.getExerciseCaloriesHour())
            );
        }

        return searchResultLlist;
    }

}
