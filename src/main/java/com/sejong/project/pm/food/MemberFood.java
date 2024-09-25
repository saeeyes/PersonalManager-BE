package com.sejong.project.pm.food;

import com.sejong.project.pm.global.entity.BaseEntity;
import com.sejong.project.pm.member.Member;

import jakarta.persistence.*;
import lombok.AccessLevel;
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

}
