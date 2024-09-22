package com.sejong.project.pm.global.handler;


import com.sejong.project.pm.global.exception.BaseException;
import com.sejong.project.pm.global.exception.codes.BaseCode;

public class MyExceptionHandler extends BaseException {
    public MyExceptionHandler (BaseCode code){
        super(code);
    }
}
