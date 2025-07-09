package com.example.happyprogrambe.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "followers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "followID", length = 36)
    private String id;

    @ManyToOne
    @JoinColumn(name = "menteeID")
    private User mentee;

    @ManyToOne
    @JoinColumn(name = "mentorID")
    private MentorDetail mentor;

    private LocalDateTime timestamp;
}