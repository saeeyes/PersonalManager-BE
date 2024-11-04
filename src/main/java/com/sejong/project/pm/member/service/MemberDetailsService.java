package com.sejong.project.pm.member.service;

import com.sejong.project.pm.global.auth.member.MemberAuthContext;
import com.sejong.project.pm.global.exception.BaseException;
import com.sejong.project.pm.global.exception.codes.ErrorCode;
import com.sejong.project.pm.member.model.Member;
import com.sejong.project.pm.member.dto.MemberDetails;
import com.sejong.project.pm.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public MemberDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findMemberByMemberEmail(username)
                .orElseThrow(() -> {
                    log.info("[loadUserByUsername] username:{}, {}", username, ErrorCode.MEMBER_NOT_FOUND);
                    return new BaseException(ErrorCode.MEMBER_NOT_FOUND);
                });
        MemberAuthContext ctx = MemberAuthContext.of(member);
        return new MemberDetails(ctx);
    }
}