package com.sejong.project.pm.member.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Entity
@Table(name = "member_oauth")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberOAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_oauth_id")
    private Long id;

    @Column(name = "oauth_id") @NotNull
    private String oauthId;

    @Column(name = "provider_type") @NotNull
    @Enumerated(value = EnumType.STRING)
    private OAuthProviderType oAuthProviderType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    @Builder
    private MemberOAuth(String oauthId, OAuthProviderType oAuthProviderType) {
        this.oauthId = oauthId;
        this.oAuthProviderType = oAuthProviderType;
    }

    public static MemberOAuth createMemberOAuth(OAuthProviderType oAuthProviderType){
        return MemberOAuth.builder()
                .oauthId(UUID.randomUUID().toString())
                .oAuthProviderType(oAuthProviderType)
                .build();
    }

    public void updateMemberOAuthBy(Member member){
        this.member = member;
        member.getMemberOAuths().add(this);
    }
}
