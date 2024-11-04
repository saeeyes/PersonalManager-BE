package com.sejong.project.pm.global.auth.token;


import com.sejong.project.pm.global.auth.token.vo.AccessToken;
import com.sejong.project.pm.global.auth.token.vo.RefreshToken;
import com.sejong.project.pm.global.exception.BaseException;
import com.sejong.project.pm.global.exception.codes.ErrorCode;
import com.sejong.project.pm.member.model.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

import static com.sejong.project.pm.global.properties.JwtProperties.*;

@Getter
@Component
@Slf4j
public class JwtProvider implements TokenProvider {

    private final SecretKey SECRET_KEY;
    private final String ISS = "github.com/meeteam";


    public JwtProvider(
            @Value("${jwt.secret}") String SECRET_KEY
    ) {
        byte[] keyBytes = Base64.getDecoder()
                .decode(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        this.SECRET_KEY = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    public AccessToken generateAccessToken(Member member) {
        if (member.getMemberEmail() == null || member.getMemberEmail().isBlank()) {
            return AccessToken.of("");
        }
        return this.generateAccessToken(member.getMemberEmail());
    }

    private AccessToken generateAccessToken(String email) {
        String token = Jwts.builder()
                .claim("type", "access")
                .issuer(ISS)
                .audience().add(email).and()
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + ACCESS_TOKEN_EXPIRE_TIME))
                .signWith(SECRET_KEY)
                .compact();

        log.info("[generateAccessToken] {}", token);
        return AccessToken.of(token);
    }

    public RefreshToken generateRefreshToken(Member member) {
        if (member.getMemberEmail() == null || member.getMemberEmail().isBlank()) {
            return RefreshToken.of("");
        }
        return this.generateRefreshToken(member.getMemberEmail());
    }

    private RefreshToken generateRefreshToken(String email) {
        String token = Jwts.builder()
                .claim("type", "refresh")
                .issuer(ISS)
                .audience().add(email).and()
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(SECRET_KEY)
                .compact();

        log.info("[generateRefreshToken] {}", token);
        return RefreshToken.of(token);
    }

    public String parseAudience(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token);

            if (claims.getPayload()
                    .getExpiration()
                    .before(new Date())) {
                throw new BaseException(ErrorCode.EXPIRED_ACCESS_TOKEN);
            }

            String aud = claims.getPayload()
                    .getAudience()
                    .iterator()
                    .next();

            return aud;
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("[parseAudience] {} :{}", ErrorCode.INVALID_TOKEN, token);
            throw new BaseException(ErrorCode.INVALID_TOKEN);
        } catch (BaseException e) {
            log.warn("[parseAudience] {} :{}", ErrorCode.EXPIRED_ACCESS_TOKEN, token);
            throw new BaseException(ErrorCode.EXPIRED_ACCESS_TOKEN);
        }
    }
}
