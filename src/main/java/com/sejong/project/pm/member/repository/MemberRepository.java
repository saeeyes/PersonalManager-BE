package com.sejong.project.pm.member.repository;

import com.sejong.project.pm.member.model.Member;
import com.sejong.project.pm.member.model.OAuthProviderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Boolean existsByMemberEmail(String email);

    Optional<Member> findMemberByMemberEmail(String email);

    Optional<Member> findMemberByMemberName(String username);

    Optional<Member> findById(Long memberId);

    @Query("SELECT m " +
            "FROM Member m " +
            "JOIN m.memberOAuths mo " +
            "ON m.id = mo.member.id " +
            "WHERE mo.oauthId = :oauthId AND mo.oAuthProviderType = :loginType")
    Optional<Member> findMemberByOAuthIdAndProviderType(@Param("oauthId") String oauthId, @Param("loginType") OAuthProviderType loginType);

}
