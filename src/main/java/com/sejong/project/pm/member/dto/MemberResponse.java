package com.sejong.project.pm.member.dto;

import com.sejong.project.pm.global.auth.token.vo.TokenResponse;
import com.sejong.project.pm.member.model.Member;

import java.util.List;

public class MemberResponse {
    public record MemberKakaoSignUpResDto(
            String id,
            String name
    ){}

    public record SimpleInfo(
            String userName,
            Member.DietType dietType
    ){}

    public record MemberTokenResDto(
            TokenResponse tokenResponse
    ){
        public static MemberTokenResDto from(TokenResponse tokenResponse) {
            return new MemberTokenResDto(tokenResponse);
        }
    }

    public record TodayInfo(
        int exerciseCalories,
        int eatingCaloreis,
        List<String>morning,
        List<String> lunch,
        List<String> dinner,
        List<String> snack
    ){}
}
