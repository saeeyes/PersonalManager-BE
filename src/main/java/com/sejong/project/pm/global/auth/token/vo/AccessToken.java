package com.sejong.project.pm.global.auth.token.vo;

public record AccessToken(
        String token
) {
    public static AccessToken of(String token) {
        return new AccessToken(token);
    }
}