package com.sejong.project.pm.member.service;

import com.sejong.project.pm.global.auth.member.MemberAuthContext;
import com.sejong.project.pm.global.auth.token.JwtProvider;
import com.sejong.project.pm.global.auth.token.KakaoProvider;
import com.sejong.project.pm.global.auth.token.vo.AccessToken;
import com.sejong.project.pm.global.auth.token.vo.RefreshToken;
import com.sejong.project.pm.global.auth.token.vo.TokenResponse;
import com.sejong.project.pm.global.exception.BaseException;
import com.sejong.project.pm.member.dto.MemberDetails;
import com.sejong.project.pm.member.dto.MemberRequest;
import com.sejong.project.pm.member.model.Member;
import com.sejong.project.pm.member.model.MemberOAuth;
import com.sejong.project.pm.member.dto.MemberResponse;
import com.sejong.project.pm.member.model.OAuthProviderType;
import com.sejong.project.pm.member.repository.MemberOAuthRepository;
import com.sejong.project.pm.member.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.sejong.project.pm.global.exception.codes.ErrorCode.*;
import static com.sejong.project.pm.global.properties.JwtProperties.*;
import static com.sejong.project.pm.member.dto.MemberRequest.*;


@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberOAuthRepository memberOAuthRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final KakaoProvider kakaoProvider;

    @Transactional
    public String createMember(MemberSignupRequestDto request) {

        System.out.println("enter");

        Member member = Member.createMember(request);
        member.encodePassword(passwordEncoder.encode(request.password()));
        member.setMemberGender(request.gender());
        memberRepository.save(member);

        System.out.println("enter2");

        MemberOAuth memberOAuth = MemberOAuth.createMemberOAuth(OAuthProviderType.LOCAL);
        memberOAuth.updateMemberOAuthBy(member);
        memberOAuthRepository.save(memberOAuth);

        System.out.println("enter3");
        return "회원가입이 완료됐습니다.";
    }

    @Transactional
    public MemberResponse.MemberTokenResDto localLogin(MemberLocalLoginRequestDto request, HttpServletResponse response){

        Member member = memberRepository
                .findMemberByMemberEmail(request.email())
                .orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));

        if(!passwordEncoder.matches(request.password(), member.getMemberPassword())){
            log.error("비밀번호 틀림");
            throw new BaseException(PASSWORD_ERROR);
        }

        return MemberResponse.MemberTokenResDto.from(getTokenResponse(response, member));
    }


    @Transactional
    public MemberResponse.MemberTokenResDto kakaoLogin(String code, HttpServletResponse response) throws IOException{

        //이거는 지금 토큰을 받았을 때임, 프론트에서 코드만 받아야함

        String kakaoAccessToken = kakaoProvider.getAccessToken(code);
        Map<String, Object> userInfo = kakaoProvider.getUserInfo((kakaoAccessToken));

        System.out.println("kakaoAccessToken: " + kakaoAccessToken);

        String email = (String)userInfo.get("email");
        String nickname = (String)userInfo.get("nickname");

        System.out.println("email: " + email);
        System.out.println("nickname: " + nickname);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        MultiValueMap<String, String> responseBody = new LinkedMultiValueMap<>();
//        responseBody.add("id", id.toString());

        response.getWriter().write(responseBody.toString());
        response.sendRedirect("/signup");

        MemberRequest.MemberSignupRequestDto requestDto = new MemberSignupRequestDto(email,null,null,null,null);
        Member member = Member.createMember(requestDto);

        return MemberResponse.MemberTokenResDto.from(getTokenResponse(response, member));
    }

    public Boolean checkDuplicateId(String email) {

        if(memberRepository.existsByMemberEmail(email))
            throw new BaseException(EXIST_EMAIL);
        return true;
    }

    public String getMemberAdditionInfo(MemberAuthContext context, MemberAdditionInfoRequestDto request) {

        Member member = memberRepository.findMemberByMemberEmail(context.email())
                .orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));
//        member.addMemberAdditionInfo(request);
        return "설정 완료";
    }

    @NotNull
    private TokenResponse getTokenResponse(HttpServletResponse response, Member member) {

        AccessToken accessToken = jwtProvider.generateAccessToken(member);
        RefreshToken refreshToken = jwtProvider.generateRefreshToken(member);
        TokenResponse tokenResponse = TokenResponse.of(accessToken, refreshToken);
        response.addHeader(JWT_ACCESS_TOKEN_HEADER_NAME, JWT_ACCESS_TOKEN_TYPE + accessToken.token());

        Cookie refreshTokenCookie = new Cookie(JWT_REFRESH_TOKEN_COOKIE_NAME, refreshToken.token());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setMaxAge((int) REFRESH_TOKEN_EXPIRE_TIME);
        refreshTokenCookie.setPath("/"); //path로 지정된 곳에서만 쿠키데이터를 읽을 수 있음.
        response.addCookie(refreshTokenCookie);

        return tokenResponse;
    }

    //회원 가입 후 사용자 정보 입력 하는 공간
    // 소비 칼로리 다이어트랑, 칼로리 계산 하는거 넣기  / 회원가입 후에 정보 입력 받을 모델 필요함.
    public String profileSetting(MemberDetails memberDetails, MemberRequest.ProfileSetting request){
        //member받아오기
        Member member = memberRepository
                .findMemberByMemberEmail(memberDetails.getUsername())
                .orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));

        //사용자 칼로리 계산
        int targetCalories = calCalories(
                request.memberWeight(),
                request.memberHeight(),
                request.memberAge(),
                member.getMemberGender()
                );

        //사용자 탄단지 계산
        String carprofat = calCarprofat(member,request,targetCalories);

        member.addProfileSetting(
                request.name(),
                request.memberAge(),
                request.memberHeight(),
                request.memberWeight(),
                request.memberTargetWeight(),
                targetCalories,
                request.memberDietType(),
                member.getMemberGender(),
                carprofat
        );

        return "success";
    }

    public int calCalories(double memberWeight, double memberHeight, int memberAge, Member.Gender memberGender){

        int calories = 0;
        if(memberGender == Member.Gender.MALE){
            calories += 66.47 + (13.75 * memberWeight) + (5 * memberHeight) - (6.76 * memberAge);
        }
        else if(memberGender == Member.Gender.FEMALE){
            calories += 655.1 + (9.56 * memberWeight) + (1.85 * memberHeight) - (4.68 * memberAge);
        }
        else throw new BaseException(BAD_REQUEST);

        return calories;
    }

    public String calCarprofat(Member member, MemberRequest.ProfileSetting request,int targetCalories){
        String carprofat = "";
        String carprofatPercent = request.memberDietType().getPercent();

        List<String> percent = Arrays.stream(carprofatPercent.split(":")).toList();

        for(int i=0;i<percent.size()-1;i++){
            int tmp = Integer.parseInt(percent.get(i));
            carprofat+=(tmp+":");
        }
        carprofat += percent.get(percent.size()-1);
        return carprofat;
    }

}
