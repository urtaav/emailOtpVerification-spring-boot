package com.urtaav.emailOtpVerification.service;

import com.urtaav.emailOtpVerification.dto.RegisterRequestDto;
import com.urtaav.emailOtpVerification.dto.RegisterResponseDto;
public interface UserService {
    RegisterResponseDto register(RegisterRequestDto registerRequest);
    void verify(String email,String otp);
}
