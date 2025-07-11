package com.example.happyprogrambe.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MentorSkills")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MentorSkills {
    @Id
    @Column(name = "mentorSkillId", length = 20)
    private String mentorSkillId;

    @ManyToOne
    @JoinColumn(name = "mentorId")
    private MentorDetails mentor;

    @ManyToOne
    @JoinColumn(name = "skillId")
    private Skills skill;

    @Column(name = "yearsExperience")
    private Integer yearsExperience = 0;

    @Column(name = "rate")
    private Integer rate = 0;
}