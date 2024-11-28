package com.sejong.project.pm.eyebody.dto;

import java.time.LocalDate;

public class EyeBodyResponse {
    public record EyeBodyDto(
            String base64,
            Long imgId
    ){}

    public record ImageDto(
            String base64,
            LocalDate localDate
    ){}
}
