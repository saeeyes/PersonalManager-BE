package com.sejong.project.pm.eyebody.service;

import com.sejong.project.pm.eyebody.CustomMultipartFile;
import com.sejong.project.pm.eyebody.Eyebody;
import com.sejong.project.pm.eyebody.repository.EyebodyRepository;
import com.sejong.project.pm.global.exception.BaseException;
import com.sejong.project.pm.member.dto.MemberDetails;
import com.sejong.project.pm.member.model.Member;
import com.sejong.project.pm.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static com.sejong.project.pm.global.exception.codes.ErrorCode.MEMBER_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class EyeBodyServiceImpl implements EyebodyService{
    private final EyebodyRepository eyebodyRepository;
    private final MemberRepository memberRepository;
    private final String uploadFolderPath = "src/main/resources/static/imgs/";

    public String uploadImg(MultipartFile img, MemberDetails memberDetails){
        System.out.println("enter");
        System.out.println(memberDetails);
        System.out.println(memberDetails.getUsername());

        Member member = memberRepository
                .findMemberByMemberEmail(memberDetails.getUsername())
                .orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));

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

    public byte[] getImg(Long id) throws IOException{
        Eyebody eyebody = eyebodyRepository.findById(id).get();
        String imagePath = eyebody.getCoverImageUrl();


        FileInputStream fis = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try{
            fis = new FileInputStream(imagePath);
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }

        int readCount = 0;
        byte[] buffer = new byte[1024];
        byte[] fileArray = null;

        try{
            while((readCount = fis.read(buffer)) != -1){
                baos.write(buffer, 0, readCount);
            }
            fileArray = baos.toByteArray();
            fis.close();
            baos.close();
        } catch(IOException e){
            throw new RuntimeException("File Error");
        }
        return fileArray;
    }
    public List<String> getImgList(MemberDetails memberDetails) throws IOException{
        List<String> base64Images = new ArrayList<>();

        Member member = memberRepository
                .findMemberByMemberEmail(memberDetails.getUsername())
                .orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));

        List<Eyebody> eyebodyList = eyebodyRepository.findByMember(member);

        for(Eyebody eyebody: eyebodyList){
            String imagePath = eyebody.getCoverImageUrl();

            FileInputStream fis = null;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            try{
                fis = new FileInputStream(imagePath);
            } catch(FileNotFoundException e){
                e.printStackTrace();
            }

            int readCount = 0;
            byte[] buffer = new byte[1024];
            byte[] fileArray = null;

            try{
                while((readCount = fis.read(buffer)) != -1){
                    baos.write(buffer, 0, readCount);
                }
                fileArray = baos.toByteArray();
                String base64Image = Base64.getEncoder().encodeToString(fileArray);
                base64Images.add(base64Image);

                fis.close();
                baos.close();
            } catch(IOException e){
                throw new RuntimeException("File Error");
            }
        }
        return base64Images;
    }
}
