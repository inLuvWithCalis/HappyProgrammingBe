package com.example.happyprogrambe.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "MentorDetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MentorDetails {
    @Id
    @Column(name = "mentorId", length = 20)
    private String mentorId;

    @OneToOne
    @JoinColumn(name = "mentorId")
    @MapsId
    private User mentor;

    @Column(name = "facebook", length = 255)
    private String facebook;

    @Column(name = "github", length = 255)
    private String github;

    @Column(name = "profession", length = 255)
    private String profession;

    @Column(name = "language", length = 255)
    private String language;

    @Column(name = "introduction", length = 255)
    private String introduction;

    @Column(name = "serviceDescription", length = 255)
    private String serviceDescription;

    @Column(name = "achievementDescription", length = 255)
    private String achievementDescription;

    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MentorSkills> mentorSkills;

    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Request> requests;

    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comments> comments;

    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Followers> followers;
}