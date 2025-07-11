package com.example.happyprogrambe.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comments {
    @Id
    @Column(name = "commentId", length = 20)
    private String commentId;

    @ManyToOne
    @JoinColumn(name = "menteeId")
    private User mentee;

    @ManyToOne
    @JoinColumn(name = "mentorId")
    private MentorDetails mentor;

    @Column(name = "rate")
    private Integer rate;

    @Column(name = "comments", length = 1500)
    private String comments;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;
}
