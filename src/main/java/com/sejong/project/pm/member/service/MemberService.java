package com.sejong.project.pm.member.service;

import com.sejong.project.pm.food.MemberFood;
import com.sejong.project.pm.global.auth.member.MemberAuthContext;
import com.sejong.project.pm.member.dto.MemberDetails;
import com.sejong.project.pm.member.dto.MemberRequest;
import com.sejong.project.pm.member.dto.MemberResponse;
import com.sejong.project.pm.member.model.Member;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface MemberService {
    String createMember(MemberRequest.MemberSignupRequestDto request);
    MemberResponse.MemberTokenResDto localLogin(MemberRequest.MemberLocalLoginRequestDto request, HttpServletResponse response);
    MemberResponse.MemberTokenResDto kakaoLogin(String code, HttpServletResponse response) throws IOException;
    Boolean checkDuplicateId(String id);
    String getMemberAdditionInfo(MemberAuthContext context, MemberRequest.MemberAdditionInfoRequestDto request);

    String profileSetting(MemberDetails memberDetails, MemberRequest.ProfileSetting request);
    int calCalories(double memberWeight, double memberHeight, int memberAge, Member.Gender memberGender);
    boolean isProfile(MemberDetails memberDetails);
    MemberResponse.SimpleInfo simpleInfo(MemberDetails memberDetails);
    MemberResponse.TodayInfo eatingByDateById(Long memberId);
}
