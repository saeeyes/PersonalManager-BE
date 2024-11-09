package com.sejong.project.pm.eyebody.controller;

import com.sejong.project.pm.eyebody.service.EyebodyService;
import com.sejong.project.pm.global.exception.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class EyebodyController {

    private final EyebodyService eyebodyService;

    @ResponseBody
    @PostMapping("/api/saveImage")
    public BaseResponse<?> uploadImg(@RequestParam("img") MultipartFile img) throws Exception {
        return BaseResponse.onSuccess(eyebodyService.uploadImg(img));
    }

//    @GetMapping("/api/getImage")
//    public BaseResponse<?> getImg(Long id) throws Exception {
//        return BaseResponse.onSuccess(eyebodyService.getImg(id));
//    }
}
