package com.sejong.project.pm.exercise;

import com.sejong.project.pm.global.entity.BaseEntity;
import com.sejong.project.pm.member.model.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_exercise")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberExercise extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    private double exerciseTime;

    private int exerciseCalories;

    @Builder
    private MemberExercise(Member member, Exercise exercise, double exerciseTime, int exerciseCalories) {
        this.member = member;
        this.exercise = exercise;
        this.exerciseTime = exerciseTime;
        this.exerciseCalories = exerciseCalories;
    }

    public static MemberExercise createMemberExercise(Member member,Exercise exercise,Double exerciseTime, int exerciseCalories){
        return MemberExercise.builder()
                .member(member)
                .exercise(exercise)
                .exerciseTime(exerciseTime)
                .exerciseCalories(exerciseCalories)
                .build();
    }


}
