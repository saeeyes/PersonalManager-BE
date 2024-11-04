package com.sejong.project.pm.member.model;

import lombok.Getter;

@Getter
public enum OAuthProviderType {
    LOCAL("local"),
    KAKAO("kakao");

    private final String provider;

    OAuthProviderType(String provider) {
        this.provider = provider;
    }
}