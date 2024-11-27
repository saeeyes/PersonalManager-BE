package com.sejong.project.pm.weight.Controller;

import com.sejong.project.pm.global.exception.BaseResponse;
import com.sejong.project.pm.member.dto.MemberDetails;
import com.sejong.project.pm.weight.Service.WeightService;
import com.sejong.project.pm.weight.Weight;
import com.sejong.project.pm.weight.dto.WeightRequest;
import com.sejong.project.pm.weight.dto.WeightResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
public class WeightController {

    @Autowired
    WeightService weightService;

    @GetMapping("/checkweight")
    public  BaseResponse<?> checkweight(@AuthenticationPrincipal MemberDetails member, @RequestParam("today") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate today){
            return BaseResponse.onSuccess(weightService.checkweight(member, today));
    }

    @PostMapping("/createweight")
    public BaseResponse<?> createweight(@RequestBody WeightRequest.weightRequestDto weightRequestDto, @AuthenticationPrincipal MemberDetails member){
        weightService.createweight(weightRequestDto, member);
        return BaseResponse.onSuccess("success");
    }

    @PutMapping("/rewriteweight")
    public  BaseResponse<?> rewritewight(@AuthenticationPrincipal MemberDetails member, @RequestBody WeightRequest.weightRequestDto weightRequestDto){
        System.out.println(weightRequestDto);
        weightService.rewriteweight(member, weightRequestDto);
        return BaseResponse.onSuccess("success");
    }
    @GetMapping("dayweight")
    public BaseResponse<?> dayweight(@AuthenticationPrincipal MemberDetails member,  @RequestParam("today") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate today){
        return BaseResponse.onSuccess(weightService.dayweight(member, today));
    }

    @GetMapping("monthweight")
    public  BaseResponse<?> monthweight(@AuthenticationPrincipal MemberDetails member, @RequestParam("today") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate today){
        return BaseResponse.onSuccess( weightService.monthweight(member, today));
    }
}

