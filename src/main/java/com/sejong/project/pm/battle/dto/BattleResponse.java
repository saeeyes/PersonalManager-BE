package com.sejong.project.pm.battle.dto;

import com.sejong.project.pm.battle.BattlePhrase;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

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
            int member1AttainmentRate,
            int member2AttainmentRate,
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate targetDay
    ){
        public battlestatusDto(  String member1Name, String member2Name,
                                 String member1Image, String member2Image,
                                 double member1StartWeight, double member2StartWeight,
                                 double member1TargetWeight, double member2TargetWeight,
                                 int member1AttainmentRate, int member2AttainmentRate,
                                 LocalDate targetDay){
            this.member1Name = member1Name;
            this.member2Name = member2Name;
            this.member1Image = member1Image;
            this.member2Image = member2Image;
            this.member1StartWeight = member1StartWeight;
            this.member2StartWeight = member2StartWeight;
            this.member1TargetWeight = member1TargetWeight;
            this.member2TargetWeight = member2TargetWeight;
            this.member1AttainmentRate = member1AttainmentRate;
            this.member2AttainmentRate = member2AttainmentRate;
            this.targetDay = targetDay;
        }
    }

    public record battleListDto(
        String member1Name,
        String member2Name,
        String member1Image,
        String member2Image,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate startDay,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate targetDay
    ){public battleListDto(String member1Name, String member2Name,
                           String member1Image, String member2Image,
                           LocalDate startDay, LocalDate targetDay){
        this.member1Name = member1Name;
        this.member2Name = member2Name;
        this.member1Image = member1Image;
        this.member2Image = member2Image;
        this.startDay = startDay;
        this.targetDay = targetDay;
    }}

    public record battleResultDto(
            String memberName,
            BattlePhrase.State state,
            String phrase
    ){public battleResultDto(String memberName, BattlePhrase.State state, String phrase){
        this.memberName = memberName;
        this.state = state;
        this.phrase = phrase;
    }}
}
