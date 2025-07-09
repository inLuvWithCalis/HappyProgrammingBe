package com.example.happyprogrambe.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "mentordetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MentorDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "mentorID", length = 36)
    private String id;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "userID")
    private User user;

    private String facebook;
    private String github;
    private String profession;
    private String language;
    private String introduction;
    private String serviceDescription;
    private String achievementDescription;

    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL)
    private List<MentorSkill> skills;

    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL)
    private List<Comment> receivedComments;

    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL)
    private List<Follower> followers;
}