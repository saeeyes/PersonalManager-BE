package com.sejong.project.pm.global.exception.howToUse;

import com.sejong.project.pm.global.exception.BaseResponse;
import com.sejong.project.pm.global.exception.howToUse.service.TempService;
import com.sejong.project.pm.global.exception.howToUse.temp.TempRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TempController {

    private final TempService tempService;

    @GetMapping("/binding")
    public BaseResponse<?> bindingTest(@Validated @RequestBody TempRequest.TempLoginRequest request){
        return BaseResponse.onSuccess(tempService.logic(request));
    }

    @GetMapping("/search")
    public BaseResponse<?> validationTest(@RequestParam String keyword){
        return BaseResponse.onSuccess(tempService.searchEngine(keyword));
    }
}