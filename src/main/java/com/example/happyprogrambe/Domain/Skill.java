package com.example.happyprogrambe.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "skills")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "skillID", length = 36)
    private String id;

    @Column(length = 50)
    private String skillName;

    private Boolean status;

    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL)
    private List<MentorSkill> mentorSkills;
}