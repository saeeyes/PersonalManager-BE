package com.sejong.project.pm.post;

import com.sejong.project.pm.global.entity.BaseEntity;
import com.sejong.project.pm.member.Member;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

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

    private Member writer;

    @OneToMany(mappedBy = "post")
    private List<MemberPost> memberPostList = new ArrayList<>();
}
