package com.sejong.project.pm.member.controller;


import com.sejong.project.pm.global.auth.member.MemberAuthContext;
import com.sejong.project.pm.global.exception.BaseResponse;
import com.sejong.project.pm.member.dto.MemberDetails;
import com.sejong.project.pm.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.sejong.project.pm.member.dto.MemberRequest.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup/local")
    public BaseResponse<?> signup(@Valid @RequestBody MemberSignupRequestDto request){
        return BaseResponse.onSuccess(memberService.createMember(request));
    }

    //이메일 중복 확인
    @GetMapping("/signup/checkEmail")
    public BaseResponse<?> checkId(@RequestParam("email") String email){
        return BaseResponse.onSuccess(memberService.checkDuplicateId(email));
    }

    @PostMapping("/login/local")
    public BaseResponse<?> login(@Valid @RequestBody MemberLocalLoginRequestDto request, HttpServletResponse response){
        return BaseResponse.onSuccess(memberService.localLogin(request, response));
    }

    @PostMapping("/login/kakao")
    public BaseResponse<?> kakaoLogin(@Valid @RequestBody MemberKakaoLoginRequestDto request, HttpServletResponse response) throws IOException {
        return BaseResponse.onSuccess(memberService.kakaoLogin(request.code(), response));
    }

    //회원가입 이후에 첫 로그인시 데이터 기록 하는거

    @PostMapping("/user-info")
    public BaseResponse<?> postUserInfoAfterSignUp(@AuthenticationPrincipal MemberDetails principal,
                                                   @Valid @RequestBody MemberAdditionInfoRequestDto request){
        return BaseResponse.onSuccess(memberService.getMemberAdditionInfo(principal.context(),  request));
    }

}