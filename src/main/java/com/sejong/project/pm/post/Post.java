package com.sejong.project.pm.post;

import com.sejong.project.pm.global.entity.BaseEntity;
import com.sejong.project.pm.member.Member;
import com.sejong.project.pm.post.dto.PostRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String postTitle;

    private String postContent;

    private String exerciseName;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime meetTime;

    private String meetPlace;

    private int numberOfPeople;

    private Member.Gender recruitmentGender;

    private Long memberId;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberPost> memberPostList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostApplication> postApplications = new ArrayList<>();

    @Builder
    public Post(PostRequest.postRequestDto postRequestDto){
        this.postTitle = postRequestDto.postTitle();
        this.postContent = postRequestDto.postContent();
        this.exerciseName = postRequestDto.exerciseName();
        this.meetTime = postRequestDto.meetTime();
        this.meetPlace = postRequestDto.meetPlace();
        this.numberOfPeople = postRequestDto.numberOfPeople();
        this.recruitmentGender = postRequestDto.recruitmentGender();
        this.memberId = postRequestDto.memberId();
    }
}
