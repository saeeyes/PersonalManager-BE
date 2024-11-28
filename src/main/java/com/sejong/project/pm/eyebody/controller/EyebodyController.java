package com.sejong.project.pm.eyebody.controller;

import com.sejong.project.pm.eyebody.service.EyebodyService;
import com.sejong.project.pm.global.exception.BaseResponse;
import com.sejong.project.pm.member.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;


@RestController
@RequiredArgsConstructor
public class EyebodyController {

    private final EyebodyService eyebodyService;

    @ResponseBody
    @PostMapping("/api/saveImage")
    public BaseResponse<?> uploadImg(@RequestParam("img") MultipartFile img,@AuthenticationPrincipal MemberDetails memberDetails) throws Exception {
        return BaseResponse.onSuccess(eyebodyService.uploadImg(img,memberDetails));
    }

//    @GetMapping(value ="/api/getImage", produces= MediaType.IMAGE_PNG_VALUE)
    @GetMapping(value ="/api/getImage")
    public BaseResponse<?> getImg(@RequestParam("id") Long id) throws Exception {
        return BaseResponse.onSuccess(eyebodyService.getImg(id));
    }

    @GetMapping(value ="/api/getImageList")
    public BaseResponse<?> getImgList(@AuthenticationPrincipal MemberDetails member) throws Exception {
        return BaseResponse.onSuccess(eyebodyService.getImgList(member));
    }

    @GetMapping(value ="/api/isImageDate")
    public BaseResponse<?> isImageDate(@AuthenticationPrincipal MemberDetails member, @RequestParam("date")LocalDate date) throws Exception {
        return BaseResponse.onSuccess(eyebodyService.isImageDate(member,date));
    }

    @GetMapping(value ="/api/getImageListDate")
    public BaseResponse<?> getImageListDate(@AuthenticationPrincipal MemberDetails member, @RequestParam("date")LocalDate date) throws Exception {
        return BaseResponse.onSuccess(eyebodyService.getImageListDate(member,date));
    }

}
