package com.urtaav.emailOtpVerification.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegisterRequestDto {
    private String userName;
    private String email;
    private String password;
}
