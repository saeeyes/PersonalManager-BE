package com.sejong.project.pm.eyebody.dto;

public class EyeBodyResponse {
    public record EyeBodyDto(
            String base64,
            Long imgId
    ){}
}
