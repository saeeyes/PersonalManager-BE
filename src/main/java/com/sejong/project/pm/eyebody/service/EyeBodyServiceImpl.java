package com.sejong.project.pm.eyebody.service;

import com.sejong.project.pm.eyebody.Eyebody;
import com.sejong.project.pm.eyebody.repository.EyebodyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
@Slf4j
@RequiredArgsConstructor
public class EyeBodyServiceImpl implements EyebodyService{

    private final EyebodyRepository eyebodyRepository;

    private final String uploadFolderPath = "src/main/resources/static/imgs/";

    public String uploadImg(MultipartFile img){
        System.out.println("enter");

        try{
            String imagePath = null;
            String absolutePath = new File("").getAbsolutePath() + "\\";
            File file = new File(uploadFolderPath);

            if (!file.exists()) {
                file.mkdirs();
            }

            if (!img.isEmpty()) {
                String contentType = img.getContentType();
                String originalFileExtension;
                if (ObjectUtils.isEmpty(contentType)) {
                    throw new Exception("이미지 파일은 jpg, png 만 가능합니다.");
                } else {
                    if (contentType.contains("image/jpeg")) {
                        originalFileExtension = ".jpg";
                    } else if (contentType.contains("image/png")) {
                        originalFileExtension = ".png";
                    } else {
                        throw new Exception("이미지 파일은 jpg, png 만 가능합니다.");
                    }
                }



                Eyebody eyebodyImg = eyebodyRepository.save(
                  Eyebody.builder()
                          .title(img.getName())
                          .coverImageUrl(uploadFolderPath)
                          .member(null)
                          .build()
                );

                imagePath = uploadFolderPath + "/" + eyebodyImg.getId() +  originalFileExtension;

                eyebodyImg.setUrl(imagePath);
                eyebodyRepository.save(eyebodyImg);

                file = new File(absolutePath + imagePath);
                img.transferTo(file);
            }
            else {
                throw new Exception("이미지 파일이 비어있습니다.");
            }
            return imagePath;

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return "success";
    }

//    public MultipartFile getImg(Long id){
//
//    }

}
