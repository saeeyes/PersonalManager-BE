package com.sejong.project.pm.exercise;

import com.sejong.project.pm.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
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

}
