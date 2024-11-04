package com.sejong.project.pm.global.auth.member;

import com.sejong.project.pm.member.model.Member;
import lombok.Builder;

@Builder
public record MemberAuthContext(
        Long id,
        String name,
        String role,
        String email,
        String password
) {
    public static MemberAuthContext of(Member member) {
        return MemberAuthContext.builder()
                .email(member.getMemberEmail())
                .name(member.getMemberName())
                .password(member.getMemberPassword())
                .role(member.getRole().toString())
                .id(member.getId())
                .build();
    }
}
