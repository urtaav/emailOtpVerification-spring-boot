package com.urtaav.emailOtpVerification.controller;

import com.urtaav.emailOtpVerification.dto.RegisterRequestDto;
import com.urtaav.emailOtpVerification.dto.RegisterResponseDto;
import com.urtaav.emailOtpVerification.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody RegisterRequestDto registerRequest){
        RegisterResponseDto registerResponseDto = userService.register(registerRequest);
        return new ResponseEntity<>(registerResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestParam String email, @RequestParam String otp){
        try {
            userService.verify(email, otp);
            return new ResponseEntity<>("User verified successfully",HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}