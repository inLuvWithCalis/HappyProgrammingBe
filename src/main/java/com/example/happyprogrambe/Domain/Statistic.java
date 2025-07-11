package com.example.happyprogrambe.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Statistic")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Statistic {
    @Id
    @Column(name = "userId", length = 20)
    private String userId;

    @OneToOne
    @JoinColumn(name = "userId")
    @MapsId
    private User user;

    @Column(name = "roleId", length = 10)
    private String roleId;

    @Column(name = "totalRequests")
    private Integer totalRequests = 0;

    @Column(name = "completedRequests")
    private Integer completedRequests = 0;

    @Column(name = "canceledRequests")
    private Integer canceledRequests = 0;

    @Column(name = "avgRate")
    private Double avgRate = 0.0;

    @Column(name = "followerCount")
    private Integer followerCount = 0;

    @Column(name = "lastUpdated")
    private LocalDateTime lastUpdated;

    @ManyToOne
    @JoinColumn(name = "roleId", insertable = false, updatable = false)
    private Role role;
}