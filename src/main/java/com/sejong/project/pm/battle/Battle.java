package com.sejong.project.pm.battle;

import com.sejong.project.pm.global.entity.BaseEntity;
import com.sejong.project.pm.member.model.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "battle")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Battle extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member1;
    @ManyToOne
    private Member member2;

    private double member1TargetWeight;

    private double member2TargetWeight;

    private double member1StartWeight;

    private double member2StartWeight;

    private String targetDay;

    private String inviteCode;

}
