package com.example.happyprogrambe.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Skills")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Skills {
    @Id
    @Column(name = "skillId", length = 20)
    private String skillId;

    @Column(name = "skillName", nullable = false, length = 50)
    private String skillName;

    @Column(name = "status", nullable = false)
    private Boolean status = true;

    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MentorSkills> mentorSkills;

    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Request> requests;
}