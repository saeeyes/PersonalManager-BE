package com.sejong.project.pm.global.properties;

import org.springframework.beans.factory.annotation.Value;

public class KakaoProperties {
    @Value("${jwt.security.oauth2.client.registration.kakao.client-id}")
    public static String kakaoClientId;

    @Value("${jwt.security.oauth2.client.registration.kakao.redirect-uri}")
    public static String kakaoRedirectUri;
}
