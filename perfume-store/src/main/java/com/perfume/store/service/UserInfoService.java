package com.perfume.store.service;

import com.perfume.store.dto.RegisterUserRequest;
import com.perfume.store.enums.Roles;
import com.perfume.store.exceptions.InvalidArgumentException;
import com.perfume.store.model.OtpEntity;
import com.perfume.store.model.OtpVerificationRequest;
import com.perfume.store.model.User;
import com.perfume.store.repository.OtpRepository;
import com.perfume.store.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class UserInfoService  {

    @Autowired
    private UserRepo repository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private JwtService jwtService;


    public User addUser(RegisterUserRequest userInfo) {
        if (userInfo.getEmail() == null || userInfo.getEmail().isEmpty()) {
            throw new InvalidArgumentException("Email cannot be null or empty");
        }

        if (repository.existsByEmail(userInfo.getEmail())) {
            throw new InvalidArgumentException("A user with this email already exists: " + userInfo.getEmail());
        }
        User user = new User();
        user.setName(userInfo.getName());
        user.setPassword(encoder.encode(userInfo.getPassword()));
        user.setEmail(userInfo.getEmail());
        String role = userInfo.getRole() != null ? String.valueOf(userInfo.getRole()) : "USER";
        user.setRole(Roles.valueOf(role));

        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        repository.save(user);

        return user;
    }


    public void sendOtp(RegisterUserRequest userInfo) {
        if (repository.existsByEmail(userInfo.getEmail())) {
            throw new InvalidArgumentException("A user with this email already exists: " + userInfo.getEmail());
        }
        String otp = String.valueOf((int) (Math.random() * 900000) + 100000);
        OtpEntity otpEntity = new OtpEntity(userInfo.getEmail(), otp, LocalDateTime.now().plusMinutes(5));
        otpRepository.save(otpEntity);
        emailService.sendEmail(userInfo.getEmail(), "Your OTP", "Your OTP is: " + otp);
    }

    public User verifyOtpAndCreateUser(OtpVerificationRequest otpRequest) {
        OtpEntity otpEntity = otpRepository.findByEmail(otpRequest.getEmail())
                .orElseThrow(() -> new InvalidArgumentException(("OTP not found or expired")));

        if (!otpEntity.getOtp().equals(otpRequest.getOtp())) {
            throw new InvalidArgumentException("Invalid OTP");
        }
        User user = new User();
        user.setName(otpRequest.getName());
        user.setPassword(encoder.encode(otpRequest.getPassword()));
        user.setEmail(otpRequest.getEmail());
        user.setRole(Roles.USER);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        repository.save(user);

        otpRepository.delete(otpEntity);

        return user;
    }

    public void resetPassword(String token, String newPassword) {
        String email = jwtService.validateResetToken(token);

        User user = repository.findByEmail(email);
        if (Objects.isNull(user)) {
                throw new UsernameNotFoundException("No user found with email: " + email);
        }
        user.setPassword(encoder.encode(newPassword));
        user.setUpdatedAt(LocalDateTime.now());
        repository.save(user);
    }

    public void sendResetToken(String email) {
        User user = repository.findByEmail(email);

        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("No user found with email: " + email);
        }
        String resetToken = jwtService.generateResetToken(email);
        emailService.sendEmail(email, "Reset Password",
                "Use this token to reset your password: " + resetToken);
    }



}