package com.sejong.project.pm.exercise.controller;

import com.sejong.project.pm.exercise.dto.ExerciseRequest;
import com.sejong.project.pm.exercise.service.ExerciseService;
import com.sejong.project.pm.global.exception.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExerciseController {

    @Autowired
    private final ExerciseService exerciseService;

    @PostMapping("/exercise/save")
    private BaseResponse<?> saveExercise(@RequestBody ExerciseRequest.saveExerciseDto saveExercise){
        exerciseService.saveExercise(saveExercise);
        return BaseResponse.onSuccess("success");
    }

    @GetMapping("/exercise/search")
    private BaseResponse<?> searchExercise(@RequestBody ExerciseRequest.searchExerciseDto searchexercise){
        return BaseResponse.onSuccess(exerciseService.searchExercise(searchexercise));
    }


}
