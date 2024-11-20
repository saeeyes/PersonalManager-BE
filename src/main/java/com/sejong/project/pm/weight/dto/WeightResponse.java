package com.sejong.project.pm.weight.dto;

import java.time.LocalDate;

public class WeightResponse {

    public record weightResponseDto(
            String memberName,
            double memberWeight,
            double memberBodyfat,
            double memberSkeletalmuscle,
            LocalDate date
    ){}
    public record MonthWeightDto(
            double memberWeight,
            LocalDate date
    ){}
}
