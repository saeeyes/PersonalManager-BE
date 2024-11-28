package com.sejong.project.pm.exercise.controller;

import com.sejong.project.pm.exercise.dto.ExerciseRequest;
import com.sejong.project.pm.exercise.dto.ExerciseResponse;
import com.sejong.project.pm.exercise.service.ExerciseService;
import com.sejong.project.pm.global.exception.BaseResponse;
import com.sejong.project.pm.member.dto.MemberDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "문자열 반복", description = "파라미터로 받은 문자열을 2번 반복합니다.")
    @Parameter(name = "ExerciseResponse.searchResultDto", description = "response값")
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


    @DeleteMapping("/exercise/deleteExercise")
    private BaseResponse<?> deleteExercise(@AuthenticationPrincipal MemberDetails member, ExerciseRequest.ExerciseIdDto request){
        return BaseResponse.onSuccess(exerciseService.deleteExercise(member,request));
    }

    @DeleteMapping("/exercise/deleteMemberExercise")
    private BaseResponse<?> deleteMemberExercise(@AuthenticationPrincipal MemberDetails member, ExerciseRequest.MemberExerciseIdDto request){
        return BaseResponse.onSuccess(exerciseService.deleteMemberExercise(member,request));
    }


}
