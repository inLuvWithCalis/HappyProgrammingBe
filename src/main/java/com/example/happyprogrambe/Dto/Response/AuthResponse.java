package com.example.happyprogrambe.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String userId;
    private String username;
    private String email;
    private String fullname;
    private String phone;
    private String address;
    private LocalDate dob;
    private String sex;
    private String image;
    private String roleId;
    private String roleName;
    private String token; // JWT token if using JWT
    private Boolean status;
    private Boolean emailStatus;
}
