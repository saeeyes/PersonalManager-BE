package com.sejong.project.pm.battle.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

public class BattleResponse {
    public record battlestatusDto(
            String member1Name,
            String member2Name,
            String member1Image,
            String member2Image,
            double member1StartWeight,
            double member2StartWeight,
            double member1TargetWeight,
            double member2TargetWeight,
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            Date targetDay
    ){
        public battlestatusDto(  String member1Name, String member2Name,
                                 String member1Image, String member2Image,
                                 double member1StartWeight, double member2StartWeight,
                                 double member1TargetWeight, double member2TargetWeight,
                                 Date targetDay){
            this.member1Name = member1Name;
            this.member2Name = member2Name;
            this.member1Image = member1Image;
            this.member2Image = member2Image;
            this.member1StartWeight = member1StartWeight;
            this.member2StartWeight = member2StartWeight;
            this.member1TargetWeight = member1TargetWeight;
            this.member2TargetWeight = member2TargetWeight;
            this.targetDay = targetDay;
        }
    }

    public record battleListDto(
        String member1Name,
        String member2Name,
        String member1Image,
        String member2Image,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDateTime startDay,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        Date targetDay
    ){public battleListDto(String member1Name, String member2Name,
                           String member1Image, String member2Image,
                           LocalDateTime startDay, Date targetDay){
        this.member1Name = member1Name;
        this.member2Name = member2Name;
        this.member1Image = member1Image;
        this.member2Image = member2Image;
        this.startDay = startDay;
        this.targetDay = targetDay;
    }}
}
