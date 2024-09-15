package com.sejong.project.pm.global.handler;


import com.example.meeTeam.global.exception.BaseException;
import com.example.meeTeam.global.exception.codes.BaseCode;

public class MyExceptionHandler extends BaseException {
    public MyExceptionHandler (BaseCode code){
        super(code);
    }
}
