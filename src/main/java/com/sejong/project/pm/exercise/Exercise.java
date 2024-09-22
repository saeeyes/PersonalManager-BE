package com.sejong.project.pm.exercise;

import com.sejong.project.pm.global.entity.BaseEntity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;


public class Exercise extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String exerciseName;

    private int exerciseCaloriesHour;

    @OneToMany(mappedBy = "exercise")
    private List<MemberExercise> memberExerciseList = new ArrayList<>();

}
