package com.sejong.project.pm.post;

import com.sejong.project.pm.member.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "postapplication")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class PostApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Post postId;
    @ManyToOne
    private Member memberId;
    @Builder
    public PostApplication(Post postId, Member memberId) {
        this.memberId = memberId;
        this.postId = postId;
    }
}
