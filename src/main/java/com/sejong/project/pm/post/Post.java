package com.sejong.project.pm.post;

import com.sejong.project.pm.global.entity.BaseEntity;
import com.sejong.project.pm.member.model.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String tpostTitle;

    private String postContent;

    private String exerciseName;

    private String meetTime;

    private String meetPlace;

    private int numberOfPeople;

    private Member.Gender recruitmentGender;

    private String writer;

    @OneToMany(mappedBy = "post")
    private List<MemberPost> memberPostList = new ArrayList<>();
}
