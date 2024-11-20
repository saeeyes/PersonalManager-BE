package com.sejong.project.pm.exercise.controller;

import com.sejong.project.pm.exercise.dto.ExerciseRequest;
import com.sejong.project.pm.exercise.service.ExerciseService;
import com.sejong.project.pm.global.exception.BaseResponse;
import com.sejong.project.pm.member.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private BaseResponse<?> saveExercise(@AuthenticationPrincipal MemberDetails member, @RequestBody ExerciseRequest.saveExerciseDto saveExercise){
        exerciseService.saveExercise(saveExercise,member);
        return BaseResponse.onSuccess("success");
    }

    @GetMapping("/exercise/search")
    private BaseResponse<?> searchExercise(@AuthenticationPrincipal MemberDetails member, @RequestBody ExerciseRequest.searchExerciseDto searchexercise){
        return BaseResponse.onSuccess(exerciseService.searchExercise(searchexercise));
    }

    @GetMapping("/exercise/searchAll")
    private BaseResponse<?> searchExercise(@AuthenticationPrincipal MemberDetails member){
        return BaseResponse.onSuccess(exerciseService.searchAllExercise());
    }

    @PostMapping("/exercise/doingExercise")
    private BaseResponse<?> addDoingExercise(@AuthenticationPrincipal MemberDetails member, @RequestBody ExerciseRequest.doingExerciseDto doingExerciseDto){
        exerciseService.addDoingExercise(doingExerciseDto,member);
        return BaseResponse.onSuccess("success");
    }

    @GetMapping("/exercise/todayList")
    private BaseResponse<?> showTodayExerciseList(@AuthenticationPrincipal MemberDetails member){
        return BaseResponse.onSuccess(exerciseService.showTodayExerciseList(member));
    }

}
