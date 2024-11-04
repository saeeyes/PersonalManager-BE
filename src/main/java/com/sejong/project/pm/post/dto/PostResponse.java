package com.sejong.project.pm.post.dto;

import com.sejong.project.pm.member.Member;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class PostResponse {
    public record allpostsresponseDto(
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
            Date meetTime,
            String exerciseName,
            String meetPlace,
            int allNumberOfPeople,
            int currentNumberOfPeople,
            Member.Gender gender

    ){}
    public record mypostsresponseDto(
            String postTitle,
            String meetPlace,
            String exerciseName,
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
            Date meetTime,
            int allNumberOfPeople,
            int currentNumberOfPeople
    ){
    }
    public record membercount(
            int allNumberOfPeople,
            int currentNumberOfPeople
    ){}
}
