package com.example.happyprogrambe.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Request")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    @Id
    @Column(name = "requestId", length = 25)
    private String requestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menteeId", referencedColumnName = "userId")
    private User mentee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentorId", referencedColumnName = "mentorId")
    private MentorDetails mentor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skillId", referencedColumnName = "skillId")
    private Skills skill;

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "reqContent", length = 1000, columnDefinition = "VARCHAR(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String reqContent;

    @Column(name = "status", length = 1)
    private String status;

    @Column(name = "openedTime")
    private LocalDateTime openedTime;

    @Column(name = "approvedTime")
    private LocalDateTime approvedTime;

    @Column(name = "canceledTime")
    private LocalDateTime canceledTime;

    @Column(name = "closedTime")
    private LocalDateTime closedTime;
}
