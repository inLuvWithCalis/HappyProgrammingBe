package com.example.happyprogrambe.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mentorskills")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MentorSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "mentorSkillID", length = 36)
    private String id;

    @ManyToOne
    @JoinColumn(name = "mentorID")
    private MentorDetail mentor;

    @ManyToOne
    @JoinColumn(name = "skillID")
    private Skill skill;

    private Integer yearsExperience;
    private Integer rate;
}