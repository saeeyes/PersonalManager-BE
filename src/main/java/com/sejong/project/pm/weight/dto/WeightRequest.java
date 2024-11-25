package com.sejong.project.pm.weight.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class WeightRequest {
    public record weightRequestDto(
            double memberWeight,
            double memberSkeletalmuscle,
            double memberBodyfat,
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate today

    ){}


}
