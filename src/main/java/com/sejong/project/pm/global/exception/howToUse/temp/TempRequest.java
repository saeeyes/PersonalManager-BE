package com.sejong.project.pm.global.exception.howToUse.temp;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TempRequest {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TempLoginRequest{
        @NotBlank(message = "이메일을 입력해주세요")
        @Email(message = "이메일을 형식에 맞게 다시 입력해주세요.")
        private String email;

        @NotBlank(message = "비밀번호를 입력해주세요")
        private String password;
    }
}
