package com.perfume.store.controller;


import com.perfume.store.dto.AuthRequest;
import com.perfume.store.dto.RegisterUserRequest;
import com.perfume.store.dto.ResetPasswordRequest;
import com.perfume.store.model.OtpVerificationRequest;
import com.perfume.store.model.User;
import com.perfume.store.responses.LoginResponse;
import com.perfume.store.service.AuthenticationService;
import com.perfume.store.service.JwtService;
import com.perfume.store.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserInfoService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationService authenticate;

    //this is hidden endpoint for admin this is permitall for admin use
    @PostMapping("/addNewUserOrAdmin")
    public ResponseEntity<User> addNewUser(@RequestBody RegisterUserRequest userInfo) {
        return ResponseEntity.ok(service.addUser(userInfo));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> sendOtp(@RequestBody RegisterUserRequest userInfo) {
        service.sendOtp(userInfo);
        return ResponseEntity.ok("OTP sent to email: " + userInfo.getEmail());
    }

    @PostMapping("/verifyOtpSignup")
    public ResponseEntity<User> verifyOtp(@RequestBody OtpVerificationRequest otpRequest) {
        User user = service.verifyOtpAndCreateUser(otpRequest);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        User authenticatedUser = authenticate.authenticate(authRequest);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse()
                .setToken(jwtToken)
                .setExpiresIn(jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        service.resetPassword(request.getToken(), request.getNewPassword());
        return ResponseEntity.ok("Password reset successfully.");
    }
    @PostMapping("/sendResetToken")
    public ResponseEntity<String> sendResetToken(@RequestParam String email) {
        service.sendResetToken(email);
        return ResponseEntity.ok("Reset token sent to email: " + email);
    }


}
