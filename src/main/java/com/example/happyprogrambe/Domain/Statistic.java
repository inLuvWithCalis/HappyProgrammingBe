package com.example.happyprogrambe.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "statistics")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Statistic {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "userId", length = 36)
    private String id;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "userID")
    private User user;

    @Column(length = 20)
    private String role;

    private Integer totalRequests;
    private Integer completedRequests;
    private Integer canceledRequests;
    private Double avgRate;
    private Integer followerCount;
    private LocalDateTime lastUpdated;
}