package com.sejong.project.pm.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sejong.project.pm.member.model.Member;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

public class PostRequest {
    public record postRequestDto(
            String postTitle,
            String postContent,
            String exerciseName,
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
            LocalDateTime meetTime,
            String meetPlace,
            int numberOfPeople,
            Member.Gender recruitmentGender,
            Long memberId
    ){}

}
