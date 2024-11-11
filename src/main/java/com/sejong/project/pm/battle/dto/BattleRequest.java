package com.sejong.project.pm.battle.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class BattleRequest {

    // 배틀 요청을 저장하기 위한 DTO
    public record createBattleRequestDto(
            Long member1Id,
            double member1TargetWeight,
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            Date targetDay
    ) {}

    // 배틀 요청을 검색하기 위한 DTO
    public record acceptBattleRequestDto(
            String inviteCode,
            Long member2Id,
            double member2TargetWeight

    ) {}
}
