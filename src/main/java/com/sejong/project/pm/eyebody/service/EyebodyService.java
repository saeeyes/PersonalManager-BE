package com.sejong.project.pm.eyebody.service;
import com.sejong.project.pm.eyebody.dto.EyeBodyResponse;
import com.sejong.project.pm.member.dto.MemberDetails;
import jakarta.annotation.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface EyebodyService {
    String uploadImg(MultipartFile img, MemberDetails memberDetails);
    String getImg(Long id) throws IOException;
    List<EyeBodyResponse.EyeBodyDto> getImgList(MemberDetails memberDetails) throws IOException;
    String getImageListDate(MemberDetails memberDetails, LocalDate date);
    boolean isImageDate(MemberDetails memberDetails, LocalDate date);
//    MultipartFile uploadImg(Long id);
}
