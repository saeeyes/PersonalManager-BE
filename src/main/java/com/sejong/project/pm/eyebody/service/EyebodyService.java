package com.sejong.project.pm.eyebody.service;

import org.springframework.web.multipart.MultipartFile;

public interface EyebodyService {
    String uploadImg(MultipartFile img);
//    MultipartFile uploadImg(Long id);

}
