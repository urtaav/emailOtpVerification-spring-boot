package com.urtaav.emailOtpVerification.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegisterResponseDto {
    private String userName;
    private String email;
    private String password;
}
