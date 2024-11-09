package com.sejong.project.pm.eyebody.dto;

import org.springframework.web.multipart.MultipartFile;

public class EyebodyRequest {
    record uploadDto(
        MultipartFile file
    ){}

    record coverUploadDto(
            MultipartFile file,
            String title
    ){}
}
