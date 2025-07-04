package com.sejong.project.pm.battle.dto;

import com.sejong.project.pm.battle.BattlePhrase;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class BattleResponse {
    public record battlestatusDto(
            Long opponentId,
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
        public battlestatusDto(  Long opponentId,
                                 String member1Name, String member2Name,
                                 String member1Image, String member2Image,
                                 double member1StartWeight, double member2StartWeight,
                                 double member1TargetWeight, double member2TargetWeight,
                                 int member1AttainmentRate, int member2AttainmentRate,
                                 LocalDate targetDay){
            this.opponentId = opponentId;
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
        Long battleId,
        String opponentName,
        String opponentImage,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate startDay,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate targetDay
    ){public battleListDto(Long battleId, String opponentName, String opponentImage,
                           LocalDate startDay, LocalDate targetDay){
        this.battleId = battleId;
        this.opponentName = opponentName;
        this.opponentImage = opponentImage;
        this.startDay = startDay;
        this.targetDay = targetDay;
    }}

    public record battleResultDto(
            String memberName,
            BattlePhrase.State memberState,
            String opponentName,
            BattlePhrase.State opponentState,
            String phrase
    ){public battleResultDto(String memberName, BattlePhrase.State memberState, String opponentName, BattlePhrase.State opponentState,String phrase){
        this.memberName = memberName;
        this.memberState = memberState;
        this.opponentName = opponentName;
        this.opponentState = opponentState;

        this.phrase = phrase;
    }}
}
