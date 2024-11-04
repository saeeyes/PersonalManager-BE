package com.sejong.project.pm.member.dto;

import com.sejong.project.pm.global.auth.token.vo.TokenResponse;

public class MemberResponse {
    public record MemberKakaoSignUpResDto(
            String id,
            String name
    ){}

    public record MemberTokenResDto(
            TokenResponse tokenResponse
    ){
        public static MemberTokenResDto from(TokenResponse tokenResponse) {
            return new MemberTokenResDto(tokenResponse);
        }
    }
}
