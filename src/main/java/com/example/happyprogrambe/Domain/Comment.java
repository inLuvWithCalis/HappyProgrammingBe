package com.example.happyprogrambe.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "commentID", length = 10)
    private String id;

    @ManyToOne
    @JoinColumn(name = "menteeID")
    private User mentee;

    @ManyToOne
    @JoinColumn(name = "mentorID")
    private MentorDetail mentor;

    private Integer rate;

    @Column(name = "comments", length = 1500)
    private String content;

    private LocalDateTime timestamp;
}