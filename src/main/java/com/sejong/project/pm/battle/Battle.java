package com.sejong.project.pm.battle;

import com.sejong.project.pm.global.entity.BaseEntity;
import com.sejong.project.pm.member.model.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "battle")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Battle extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long battleId;

    @ManyToOne
    private Member member1Id;
    @ManyToOne
    private Member member2Id;

    private double member1TargetWeight;

    private double member2TargetWeight;

    private double member1StartWeight;

    private double member2StartWeight;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate targetDay;

    private String inviteCode;

    @Builder
    public Battle(Member member1Id, double member1StartWeight, double member1TargetWeight, LocalDate targetDay){
        this.member1Id = member1Id;
        this.member1StartWeight = member1StartWeight;
        this.member1TargetWeight = member1TargetWeight;
        this.targetDay = targetDay;
    }


}
