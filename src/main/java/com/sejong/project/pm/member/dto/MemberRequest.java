package com.sejong.project.pm.member.dto;

import com.sejong.project.pm.member.model.Member;
import com.sejong.project.pm.member.model.OAuthProviderType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.springframework.web.multipart.MultipartFile;

public class MemberRequest{
    public record MemberLocalLoginRequestDto(
            @Email String email,
            @NotEmpty String password
    ){}

    public record MemberSignupRequestDto(
            @Email String email,
            @NotEmpty String password,
            @Pattern(regexp = "^010\\d{4}\\d{4}$", message = "핸드폰 번호 형식이 올바르지 않습니다.") String phoneNumber,
            Member.Gender gender,
            OAuthProviderType loginType
    ){}

    public record MemberKakaoLoginRequestDto(
            @NotEmpty String code
    ){}

    public record MemberAdditionInfoRequestDto(
            @NotEmpty String name,
            double latitude,
            double longitude
    ){}

    public record ProfileSetting(
            @NotEmpty String name,
            int memberAge,
            double memberHeight,
            double memberWeight,
            double memberTargetWeight,
            Member.DietType memberDietType
    ){}
}