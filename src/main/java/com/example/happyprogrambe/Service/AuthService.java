package com.example.happyprogrambe.Service;

import com.example.happyprogrambe.Domain.*;
import com.example.happyprogrambe.Dto.Request.LogoutRequest;
import com.example.happyprogrambe.Dto.Request.SignInRequest;
import com.example.happyprogrambe.Dto.Request.SignUpRequest;
import com.example.happyprogrambe.Dto.Response.AuthResponse;
import com.example.happyprogrambe.Dto.Response.LogoutResponse;
import com.example.happyprogrambe.Repository.MentorDetailsRepository;
import com.example.happyprogrambe.Repository.RoleRepository;
import com.example.happyprogrambe.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MentorDetailsRepository mentorDetailsRepository;

    @Autowired
    private IdGeneratorService idGeneratorService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityContextRepository securityContextRepository;

    @Transactional
    public AuthResponse signUp(SignUpRequest request) {
        // Check if username or email already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Validate role
        Role role = roleRepository.findById(request.getRoleId()).orElseThrow(() -> new RuntimeException("Invalid role"));

        // Only allow registration for Mentor (R002) or Mentee (R003)
        if (!request.getRoleId().equals("R002") && !request.getRoleId().equals("R003")) {
            throw new RuntimeException("Only Mentor and Mentee roles are allowed for registration");
        }

        // Create new user
        String userId = idGeneratorService.generateUserId();

        User user = User.builder().userId(userId).username(request.getUsername()).email(request.getEmail()).fullname(request.getFullname()).password(passwordEncoder.encode(request.getPassword())).phone(request.getPhone()).address(request.getAddress()).dob(request.getDob()).sex(request.getSex()).role(role).status(true).emailStatus(false).build();

        // Save user first
        user = userRepository.save(user);

        // If user is a mentor, create mentor details after user is saved
        if (request.getRoleId().equals("R002")) {
            MentorDetails mentorDetails = new MentorDetails();
            mentorDetails.setMentor(user);
            mentorDetails.setFacebook(request.getFacebook());
            mentorDetails.setGithub(request.getGithub());
            mentorDetails.setProfession(request.getProfession());
            mentorDetails.setLanguage(request.getLanguage());
            mentorDetails.setIntroduction(request.getIntroduction());
            mentorDetails.setServiceDescription(request.getServiceDescription());
            mentorDetails.setAchievementDescription(request.getAchievementDescription());

            mentorDetailsRepository.save(mentorDetails);
        }

        return mapToAuthResponse(user);
    }

    public AuthResponse signIn(SignInRequest request, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        if (!user.getStatus()) {
            throw new RuntimeException("Account is disabled");
        }

        // Create authentication token and set it in SecurityContext
        org.springframework.security.authentication.UsernamePasswordAuthenticationToken authToken =
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        user.getUsername(), null, java.util.Collections.emptyList());

        org.springframework.security.core.context.SecurityContext context =
                org.springframework.security.core.context.SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authToken);
        org.springframework.security.core.context.SecurityContextHolder.setContext(context);

        // Save SecurityContext to session
        securityContextRepository.saveContext(context, httpRequest, httpResponse);

        return mapToAuthResponse(user);
    }

    public LogoutResponse logout(LogoutRequest request, HttpServletRequest httpRequest) {
        try {
            // Validate user exists
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Clear SecurityContext
            org.springframework.security.core.context.SecurityContextHolder.clearContext();

            // Create logout response
            LogoutResponse response = new LogoutResponse();
            response.setUserId(user.getUserId());
            response.setUsername(user.getUsername());
            response.setMessage("Logout successful");
            response.setLogoutTime(LocalDateTime.now().toString());

            return response;
        } catch (Exception e) {
            throw new RuntimeException("Logout failed: " + e.getMessage());
        }
    }

    private AuthResponse mapToAuthResponse(User user) {
        return AuthResponse.builder().userId(user.getUserId()).username(user.getUsername()).email(user.getEmail()).fullname(user.getFullname()).phone(user.getPhone()).address(user.getAddress()).dob(user.getDob()).sex(user.getSex() != null ? user.getSex().toString() : null).image(user.getImage()).roleId(user.getRole().getRoleId()).roleName(user.getRole().getRoleName()).status(user.getStatus()).emailStatus(user.getEmailStatus()).build();
    }
}
