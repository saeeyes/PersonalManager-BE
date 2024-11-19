package com.sejong.project.pm.food;

import com.sejong.project.pm.global.entity.BaseEntity;
import com.sejong.project.pm.member.model.Member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_food")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberFood extends BaseEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private long id;

    private double eatingAmount;

    private int eatingCalories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    private Food food;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public MemberFood(double eatingAmount, int eatingCalories, Food food, Member member) {
        this.eatingAmount = eatingAmount;
        this.eatingCalories = eatingCalories;
        this.food = food;
        this.member = member;
    }

}
