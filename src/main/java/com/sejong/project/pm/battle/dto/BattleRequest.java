package com.sejong.project.pm.battle.dto;

import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

public class BattleRequest {

    // 배틀 요청을 저장하기 위한 DTO
    public record createBattleRequestDto(
            double member1TargetWeight,
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate targetDay
    ) {}

    // 배틀 요청을 검색하기 위한 DTO
    public record acceptBattleRequestDto(
            String inviteCode,
            double member2TargetWeight

    ) {}
}
