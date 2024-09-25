package com.sejong.project.pm.exercise;

import com.sejong.project.pm.global.entity.BaseEntity;
import com.sejong.project.pm.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
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

    private int exerciseTime;

    private int exerciseCalories;


}
