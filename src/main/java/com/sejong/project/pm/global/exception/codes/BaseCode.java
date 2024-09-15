package com.sejong.project.pm.global.exception.codes;


import com.sejong.project.pm.global.exception.codes.reason.Reason;

public interface BaseCode {
    public Reason.ReasonDto getReasonHttpStatus();
}
