package com.example.happyprogrambe.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @Column(name = "userId", length = 20)
    private String userId;

    @Column(name = "username", nullable = false, length = 20)
    private String username;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "fullname", nullable = false, length = 50)
    private String fullname;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "phone", length = 10)
    private String phone;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "dob")
    private LocalDate dob;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private Gender sex;

    @Column(name = "image", length = 255)
    private String image;

    @Column(name = "status", nullable = false)
    private Boolean status = true;

    @Column(name = "emailStatus", nullable = false)
    private Boolean emailStatus = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roleId", nullable = false)
    private Role role;

    @OneToOne(mappedBy = "mentor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private MentorDetails mentorDetails;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Statistic statistic;

    public enum Gender {
        M, F, O
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.getRoleName().toUpperCase()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status != null && status;
    }
}