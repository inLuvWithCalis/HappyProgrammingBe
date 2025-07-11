package com.example.happyprogrambe.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Followers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Followers {
    @Id
    @Column(name = "followId", length = 20)
    private String followId;

    @ManyToOne
    @JoinColumn(name = "menteeId")
    private User mentee;

    @ManyToOne
    @JoinColumn(name = "mentorId")
    private MentorDetails mentor;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;
}