package com.urtaav.emailOtpVerification.service.impl;

import com.urtaav.emailOtpVerification.dto.RegisterRequestDto;
import com.urtaav.emailOtpVerification.dto.RegisterResponseDto;
import com.urtaav.emailOtpVerification.model.User;
import com.urtaav.emailOtpVerification.repository.UserRepository;
import com.urtaav.emailOtpVerification.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository usersRepository;
    private final EmailService emailService;

    @Override
    public RegisterResponseDto register(RegisterRequestDto registerRequest) {
        Optional<User> existingUser = usersRepository.findByEmail(registerRequest.getEmail());
        if (existingUser.isPresent() && existingUser.get().isVerified()) {
            throw new RuntimeException("User Already Registered");
        }

        User user = User.builder().username(registerRequest.getUserName()).email(registerRequest.getEmail()).password(registerRequest.getPassword()).build();
        String otp = generateOTP();
        user.setOtp(otp);
        User savedUser = usersRepository.save(user);
        sendVerificationEmail(savedUser.getEmail(), otp);
        return RegisterResponseDto.builder()
                .userName(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    @Override
    public void verify(String email, String otp) {
        User user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException ("User not found"));

        if (user.isVerified()) {
            throw new RuntimeException ("User is already verified");
        }

        if (Objects.equals(otp, user.getOtp())) {
            user.setVerified(true);
            usersRepository.save(user);
        } else {
            throw new RuntimeException ("Internal Server error");
        }
    }

    private String generateOTP() {
        Random random = new Random();
        int otpValue = 100000 + random.nextInt(900000);
        return String.valueOf(otpValue);
    }

    private void sendVerificationEmail(String email, String otp) {
        String subject = "Email verification Spring boot Otp example";
        String body = "your verification otp is: " + otp;
        emailService.sendEmail(email, subject, body);
    }
}
