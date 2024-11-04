package com.sejong.project.pm.global.auth.token;

import com.sejong.project.pm.global.auth.token.vo.AccessToken;
import com.sejong.project.pm.global.auth.token.vo.RefreshToken;
import com.sejong.project.pm.member.model.Member;

public interface TokenProvider {
    AccessToken generateAccessToken(Member member);

    RefreshToken generateRefreshToken(Member member);
}
