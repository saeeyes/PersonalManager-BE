package com.sejong.project.pm.eyebody.service;

import com.sejong.project.pm.member.dto.MemberDetails;
import jakarta.annotation.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface EyebodyService {
    String uploadImg(MultipartFile img, MemberDetails memberDetails);
    byte[] getImg(Long id) throws IOException;
    List<String> getImgList(MemberDetails memberDetails) throws IOException;
//    MultipartFile uploadImg(Long id);
}
