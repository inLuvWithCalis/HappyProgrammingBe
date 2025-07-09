package com.example.happyprogrambe.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "userID", length = 36)
    private String id;

    @Column(length = 20)
    private String username;

    @Column(length = 255)
    private String email;

    @Column(length = 50)
    private String fullname;

    @Column(length = 20)
    private String password;

    @Column(length = 10)
    private String phone;

    private String address;
    private LocalDate dob;

    @Column(length = 1)
    private String sex;

    private String image;
    private Boolean status;
    private Boolean emailStatus;

    // Relationships
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private MentorDetail mentorDetail;

    @OneToMany(mappedBy = "mentee", cascade = CascadeType.ALL)
    private List<Request> requests;

    @OneToMany(mappedBy = "mentee", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "mentee", cascade = CascadeType.ALL)
    private List<Follower> following;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Statistic statistic;
}