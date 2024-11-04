package com.sejong.project.pm.exercise.service;

import com.sejong.project.pm.exercise.Exercise;
import com.sejong.project.pm.exercise.MemberExercise;
import com.sejong.project.pm.exercise.dto.ExerciseRequest;
import com.sejong.project.pm.exercise.dto.ExerciseResponse;
import com.sejong.project.pm.exercise.repository.ExerciseRepository;
import com.sejong.project.pm.exercise.repository.MemberExerciseRepository;
import com.sejong.project.pm.global.handler.MyExceptionHandler;
import com.sejong.project.pm.member.model.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.sejong.project.pm.global.exception.codes.ErrorCode.BAD_REQUEST_ERROR;
import static com.sejong.project.pm.global.exception.codes.ErrorCode.NOT_VALID_ERROR;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService{

    public final ExerciseRepository exerciseRepository;
    public final MemberExerciseRepository memberExerciseRepository;

    public void addDoingExercise(ExerciseRequest.doingExerciseDto doingExerciseDto){
        //member받아와야함
        Member member = null;
        Exercise exercise = exerciseRepository.findByExerciseName(doingExerciseDto.exerciseName()).orElseThrow(() -> new MyExceptionHandler(BAD_REQUEST_ERROR));
        int calories = exercise.getExerciseCaloriesHour()*(int)doingExerciseDto.exerciseTime();
        MemberExercise memberExercise = MemberExercise.createMemberExercise(
                member,
                exercise,
                doingExerciseDto.exerciseTime(),
                calories
        );
        memberExerciseRepository.save(memberExercise);
    }

    public List<ExerciseResponse.doingExerciseDto> showTodayExerciseList(){
        //member받아와야함
        Member member =  null;
        List<MemberExercise> memberExerciseList = memberExerciseRepository.findByMember(member);
        if(memberExerciseList.isEmpty() || memberExerciseList==null) throw new MyExceptionHandler(BAD_REQUEST_ERROR);

        List<ExerciseResponse.doingExerciseDto> doingExerciseDto = new ArrayList<>();
        LocalDate now = LocalDate.now();

        for(MemberExercise me : memberExerciseList){
            if(now.getDayOfMonth() == me.getCreatedAt().getDayOfMonth()){
                //date같을 경우임

                int doingCalories = me.getExercise().getExerciseCaloriesHour()*(int)me.getExerciseTime();

                doingExerciseDto.add(new ExerciseResponse.doingExerciseDto(
                    me.getExercise().getExerciseName(),
                        doingCalories,
                        me.getCreatedAt()
                ));
            }
        }

        return doingExerciseDto;
    }

    public void saveExercise(ExerciseRequest.saveExerciseDto saveExercise){
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
                    new ExerciseResponse.searchResultDto(e.getExerciseName(),e.getExerciseCaloriesHour())
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
                    new ExerciseResponse.searchResultDto(e.getExerciseName(),e.getExerciseCaloriesHour())
            );
        }

        return searchResultLlist;
    }

}
