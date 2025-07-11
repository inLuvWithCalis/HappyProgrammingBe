package com.example.happyprogrambe.Controller.Auth;

import com.example.happyprogrambe.Dto.Request.SignInRequest;
import com.example.happyprogrambe.Dto.Request.SignUpRequest;
import com.example.happyprogrambe.Dto.Response.ApiResponse;
import com.example.happyprogrambe.Dto.Response.AuthResponse;
import com.example.happyprogrambe.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Validated
@CrossOrigin(origins = "*")
public class AuthenticationController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<AuthResponse>> signUp(@Valid @RequestBody SignUpRequest request) {
        try {
            AuthResponse response = authService.signUp(request);
            return ResponseEntity.ok(ApiResponse.success("User registered successfully", response));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(400, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error(500, "Internal server error"));
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<AuthResponse>> signIn(@Valid @RequestBody SignInRequest request) {
        try {
            AuthResponse response = authService.signIn(request);
            return ResponseEntity.ok(ApiResponse.success("Login successful", response));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(400, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error(500, "Internal server error"));
        }
    }

    @GetMapping("/test")
    public ResponseEntity<ApiResponse<String>> test() {
        return ResponseEntity.ok(ApiResponse.success("Authentication API is working"));
    }
}
