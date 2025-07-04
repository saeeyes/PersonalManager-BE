package com.sejong.project.pm.exercise.service;

import com.sejong.project.pm.exercise.dto.ExerciseRequest;
import com.sejong.project.pm.exercise.dto.ExerciseResponse;
import com.sejong.project.pm.member.dto.MemberDetails;
import com.sejong.project.pm.member.model.Member;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface ExerciseService {

    void addDoingExercise(ExerciseRequest.doingExerciseDto doingExerciseDto,@AuthenticationPrincipal MemberDetails member);
    List<ExerciseResponse.doingExerciseDto> showTodayExerciseList(@AuthenticationPrincipal MemberDetails member);
    List<ExerciseResponse.doingExerciseDto> showDateExerciseList(@AuthenticationPrincipal MemberDetails member, LocalDate localDate);

    void saveExercise(ExerciseRequest.saveExerciseDto saveExercise,@AuthenticationPrincipal MemberDetails member);
    List<ExerciseResponse.searchResultDto> searchExercise(ExerciseRequest.searchExerciseDto searchexercise);
    List<ExerciseResponse.searchResultDto> searchAllExercise();

    List<ExerciseResponse.searchResultDto> deleteExercise(MemberDetails member, ExerciseRequest.ExerciseIdDto request);
    Integer todayExercisingCalories(Member member);

    String deleteMemberExercise(MemberDetails memberDetails, ExerciseRequest.MemberExerciseIdDto request);
}
