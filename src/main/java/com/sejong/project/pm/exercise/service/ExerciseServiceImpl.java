package com.sejong.project.pm.exercise.service;

import com.sejong.project.pm.exercise.Exercise;
import com.sejong.project.pm.exercise.MemberExercise;
import com.sejong.project.pm.exercise.dto.ExerciseRequest;
import com.sejong.project.pm.exercise.dto.ExerciseResponse;
import com.sejong.project.pm.exercise.repository.ExerciseRepository;
import com.sejong.project.pm.exercise.repository.MemberExerciseRepository;
import com.sejong.project.pm.food.MemberFood;
import com.sejong.project.pm.global.exception.BaseException;
import com.sejong.project.pm.global.handler.MyExceptionHandler;
import com.sejong.project.pm.member.dto.MemberDetails;
import com.sejong.project.pm.member.dto.MemberRequest;
import com.sejong.project.pm.member.model.Member;
import com.sejong.project.pm.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.sejong.project.pm.global.exception.codes.ErrorCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService{

    public final ExerciseRepository exerciseRepository;
    public final MemberExerciseRepository memberExerciseRepository;
    public final MemberRepository memberRepository;

    public void addDoingExercise(ExerciseRequest.doingExerciseDto doingExerciseDto,@AuthenticationPrincipal MemberDetails memberDetails){
        Member member = memberRepository
                .findMemberByMemberEmail(memberDetails.getUsername())
                .orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));

        Exercise exercise = exerciseRepository.findById(doingExerciseDto.exerciseId()).orElseThrow(() -> new MyExceptionHandler(BAD_REQUEST_ERROR));

        int calories = exercise.getExerciseCaloriesHour()*(int)doingExerciseDto.exerciseTime();
        MemberExercise memberExercise = MemberExercise.createMemberExercise(
                member,
                exercise,
                doingExerciseDto.exerciseTime(),
                calories
        );
        memberExerciseRepository.save(memberExercise);
    }

    public List<ExerciseResponse.doingExerciseDto> showTodayExerciseList(@AuthenticationPrincipal MemberDetails memberDetails){
        Member member = memberRepository
                .findMemberByMemberEmail(memberDetails.getUsername())
                .orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));

        List<MemberExercise> memberExerciseList = memberExerciseRepository.findByMember(member);
        if(memberExerciseList.isEmpty() || memberExerciseList==null) throw new MyExceptionHandler(BAD_REQUEST_ERROR);

        List<ExerciseResponse.doingExerciseDto> doingExerciseDto = new ArrayList<>();
        LocalDate now = LocalDate.now();

        for(MemberExercise me : memberExerciseList){
            if(now.equals(me.getCreatedAt().toLocalDate())){
                int doingCalories = me.getExercise().getExerciseCaloriesHour()*(int)me.getExerciseTime();

                doingExerciseDto.add(new ExerciseResponse.doingExerciseDto(
                    me.getExercise().getExerciseName(),
                        doingCalories,
                        me.getExerciseTime(),
                        me.getId()
                ));
            }
        }

        return doingExerciseDto;
    }

    public void saveExercise(ExerciseRequest.saveExerciseDto saveExercise,@AuthenticationPrincipal MemberDetails member){
        //중복체크할 필요가 있을까
        Exercise exercise = Exercise.createExercise(saveExercise);
        exerciseRepository.save(exercise);
    }

    public List<ExerciseResponse.searchResultDto> searchExercise(ExerciseRequest.searchExerciseDto searchexercise){
        List<ExerciseResponse.searchResultDto> searchResultLlist = new ArrayList<>();

        List<Exercise> searchFromDb = exerciseRepository.findByExerciseNameStartingWith(searchexercise.exerciseName());

        if(searchFromDb == null || searchFromDb.isEmpty()){
             throw new MyExceptionHandler(NOT_VALID_ERROR);
        }

        for(Exercise e : searchFromDb){
            searchResultLlist.add(
                    new ExerciseResponse.searchResultDto(e.getExerciseName(),e.getExerciseCaloriesHour(),e.getId())
            );
        }

        return searchResultLlist;
    }

    public List<ExerciseResponse.searchResultDto> searchAllExercise(){
        List<ExerciseResponse.searchResultDto> searchResultLlist = new ArrayList<>();

        List<Exercise> searchFromDb = exerciseRepository.findAll();

        if(searchFromDb == null || searchFromDb.isEmpty()){
            throw new MyExceptionHandler(NOT_VALID_ERROR);
        }

        for(Exercise e : searchFromDb){
            searchResultLlist.add(
                    new ExerciseResponse.searchResultDto(e.getExerciseName(),e.getExerciseCaloriesHour(),e.getId())
            );
        }

        return searchResultLlist;
    }

    public List<ExerciseResponse.searchResultDto> deleteExercise(MemberDetails member, ExerciseRequest.ExerciseIdDto request){
        Exercise exercise = exerciseRepository.findById(request.exerciseId()).orElseThrow(() -> new BaseException(BAD_REQUEST));;
        exerciseRepository.delete(exercise);
        return searchAllExercise();
    }

    public Integer todayExercisingCalories(Member member){
        int calorie=0;
        List<MemberExercise> memberExercises = memberExerciseRepository.findByMember(member);
        List<MemberExercise> todayMemberExercises = new ArrayList<>();

        for(MemberExercise me : memberExercises){
            if(me.getCreatedAt().toLocalDate().equals(LocalDate.now())) todayMemberExercises.add(me);
        }

        for(MemberExercise me: todayMemberExercises){
            calorie+= me.getExerciseCalories();
        }

        return calorie;
    }
    public String deleteMemberExercise(MemberDetails memberDetails, ExerciseRequest.MemberExerciseIdDto request){
        Member member = memberRepository
                .findMemberByMemberEmail(memberDetails.getUsername())
                .orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));

        MemberExercise memberExercise = memberExerciseRepository.findById(request.memberExerciseId())
                .orElseThrow(() -> new BaseException(BAD_REQUEST));

        memberExerciseRepository.delete(memberExercise);

        return "success";
    }
}
