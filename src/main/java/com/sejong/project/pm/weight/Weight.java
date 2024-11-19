package com.sejong.project.pm.weight;

import com.sejong.project.pm.global.entity.BaseEntity;
import com.sejong.project.pm.member.model.Member;
import com.sejong.project.pm.weight.dto.WeightRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "weight")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Weight extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double memberWeight;

    private double memberBodyfat;

    private double memberSkeletalmuscle;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate today;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Weight(WeightRequest.weightRequestDto weightRequestDto, Member member){
        this.memberWeight = weightRequestDto.memberWeight();
        this.memberBodyfat = weightRequestDto.memberBodyfat();
        this.memberSkeletalmuscle = weightRequestDto.memberSkeletalmuscle();
        this.today = weightRequestDto.today();
        this.member = member;
    }
}
