package com.sejong.project.pm.exercise;

import com.sejong.project.pm.exercise.dto.ExerciseRequest;
import com.sejong.project.pm.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "exercise")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Exercise extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String exerciseName;

    private int exerciseCaloriesHour;

    @OneToMany(mappedBy = "exercise")
    private List<MemberExercise> memberExerciseList = new ArrayList<>();

    @Builder
    private Exercise(String exerciseName, int exerciseCaloriesHour, List<MemberExercise> memberExerciseList){
        this.exerciseName = exerciseName;
        this.exerciseCaloriesHour = exerciseCaloriesHour;
        this.memberExerciseList = memberExerciseList;
    }

    public static Exercise createExercise(ExerciseRequest.saveExerciseDto dto){
        return Exercise.builder()
                .exerciseName(dto.exerciseName())
                .exerciseCaloriesHour(dto.exerciseCaloriesHour())
                .memberExerciseList(null)
                .build();
    }

}
