package com.sejong.project.pm.post;

import com.sejong.project.pm.global.entity.BaseEntity;
import com.sejong.project.pm.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.internal.util.stereotypes.Lazy;

@Entity
@Table(name = "member_post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberPost extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    private MemberPost(Member member, Post post){
        this.member = member;
        this.post = post;
    }

    public static MemberPost createMemberPost(Member member, Post post){
        return MemberPost.builder()
                .member(member)
                .post(post)
                .build();
    }
}
