package com.sejong.project.pm.post.dto;

import com.sejong.project.pm.member.model.Member;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class PostResponse {
    public record allpostsresponseDto(
            Long postId,
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
            LocalDateTime meetTime,
            String exerciseName,
            String meetPlace,
            int allNumberOfPeople,
            int currentNumberOfPeople,
            Member.Gender gender

    ){}
    public record mypostsresponseDto(
            Long postId,
            String postTitle,
            String meetPlace,
            String exerciseName,
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
            LocalDateTime meetTime,
            int allNumberOfPeople,
            int currentNumberOfPeople
    ){
    }
    public record membercount(
            int allNumberOfPeople,
            int currentNumberOfPeople
    ){}
}
