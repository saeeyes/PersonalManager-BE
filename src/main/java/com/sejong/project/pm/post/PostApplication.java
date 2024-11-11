package com.sejong.project.pm.post;

import com.sejong.project.pm.global.entity.BaseEntity;
import com.sejong.project.pm.member.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "postapplication")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PostApplication extends BaseEntity {
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
    public PostApplication(Post postId, Member memberId) {
        this.member = memberId;
        this.post = postId;
    }
    public static PostApplication createPostApplication(Member memberId, Post postId){
        return PostApplication.builder()
                .memberId(memberId)
                .postId(postId)
                .build();
    }
}
