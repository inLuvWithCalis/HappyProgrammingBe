package com.example.happyprogrambe.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "requestId", length = 36)
    private String id;

    @ManyToOne
    @JoinColumn(name = "menteeId")
    private User mentee;

    @Column(length = 255)
    private String mentorId;

    @Column(length = 50)
    private String skillsId;

    private LocalDateTime deadline;

    @Column(length = 255)
    private String title;

    @Column(name = "reqContent")
    private String content;

    @Column(length = 1)
    private String status;

    private LocalDateTime openedTime;
    private LocalDateTime approvedTime;
    private LocalDateTime canceledTime;
    private LocalDateTime closedTime;
}