package com.sejong.project.pm.global.exception.howToUse.service;

import com.sejong.project.pm.global.exception.howToUse.temp.TempRequest;
import com.sejong.project.pm.global.handler.MyExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.sejong.project.pm.global.exception.codes.ErrorCode.*;
@Service
@Slf4j
public class TempServiceImpl implements TempService {

    public String logic(TempRequest.TempLoginRequest data) {
        log.info("service in");
        errorCheck(data);
        return "아 시발 하기 싫어";
    }

    public String searchEngine(String keyword) {
        log.info("keyword : {}", keyword);
        errorCheck(keyword);
        return "I wanna go home";
    }


    public void errorCheck(String keyword) {
        if(keyword.length() < 3)
            throw new MyExceptionHandler(SEARCH_KEYWORD_TOO_SHORT);
    }

    public void errorCheck(TempRequest.TempLoginRequest data) {
        if(data.getPassword().equals("1234")){
            throw new MyExceptionHandler(EXIST_EMAIL);
        }
    }
}
