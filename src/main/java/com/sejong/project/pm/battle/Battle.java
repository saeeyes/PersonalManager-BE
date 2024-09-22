package com.sejong.project.pm.battle;

import com.sejong.project.pm.global.entity.BaseEntity;
import com.sejong.project.pm.member.Member;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Battle extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Member member1;

    private Member member2;

    private double member1TargetWeight;

    private double member2TargetWeight;

    private double member1StartWeight;

    private double member2StartWeight;

    private String targetDay;

    private String inviteCode;

}
