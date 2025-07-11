package com.example.happyprogrambe.Dto.Request;

import com.example.happyprogrambe.Domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    @NotBlank(message = "Username is required")
    @Size(max = 20, message = "Username must be at most 20 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Size(max = 255, message = "Email must be at most 255 characters")
    private String email;

    @NotBlank(message = "Full name is required")
    @Size(max = 50, message = "Full name must be at most 50 characters")
    private String fullname;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Role is required")
    private String roleId; // R002 for Mentor, R003 for Mentee

    @Size(max = 10, message = "Phone must be at most 10 characters")
    private String phone;

    @Size(max = 255, message = "Address must be at most 255 characters")
    private String address;

    private LocalDate dob;

    private User.Gender sex;

    // Mentor specific fields (optional)
    private String facebook;
    private String github;
    private String profession;
    private String language;
    private String introduction;
    private String serviceDescription;
    private String achievementDescription;
}
