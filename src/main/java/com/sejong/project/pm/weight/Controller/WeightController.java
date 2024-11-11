package com.sejong.project.pm.weight.Controller;

import com.sejong.project.pm.global.exception.BaseResponse;
import com.sejong.project.pm.weight.Service.WeightService;
import com.sejong.project.pm.weight.Weight;
import com.sejong.project.pm.weight.dto.WeightRequest;
import com.sejong.project.pm.weight.dto.WeightResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class WeightController {

    @Autowired
    WeightService weightService;

    @PostMapping("/createweight")
    public BaseResponse<?> createweight(@RequestBody WeightRequest.weightRequestDto weightRequestDto){
        weightService.createweight(weightRequestDto);
        return BaseResponse.onSuccess("success");
    }

    @PutMapping("/rewriteweight/{weightId}")
    public  BaseResponse<?> rewritewight(@RequestBody WeightRequest.weightRequestDto weightRequestDto, @PathVariable("weightId") Long weightId){
        weightService.rewriteweight(weightRequestDto, weightId);
        return BaseResponse.onSuccess("success");
    }
    @GetMapping("dayweight")
    public BaseResponse<?> dayweight(@RequestBody WeightRequest.searchWeightRequestDto searchWeightRequestDto){
        return BaseResponse.onSuccess(weightService.dayweight(searchWeightRequestDto));
    }

    @GetMapping("monthweight")
    public  BaseResponse<?> monthweight(@RequestBody WeightRequest.searchWeightRequestDto searchWeightRequestDto){
        return BaseResponse.onSuccess( weightService.monthweight(searchWeightRequestDto));
    }
}

